# Ltech Smart Home Integration for Home Assistant

This integration allows you to control Ltech smart home devices through Home Assistant.

## v2.6.0 更新总结（中文）

### 核心修复：蓝牙 Mesh 本地控制

本次版本修复了 Ltech 设备通过蓝牙 Mesh 协议进行本地控制的关键问题，实现灯和开关的本地 Mesh 控制，无需依赖云端 API。

#### 1. Vendor Model 控制指令修复

- **问题**：Vendor Model 消息参数格式不正确，设备无法解析控制指令
- **修复**：
  - company_id 从 `0x1121` 修正为 `0x1111`（匹配 Ltech/FeasyMesh 规范）
  - zone_bitmask 使用 16 位大端整数（3 字节参数：`[zone_hi, zone_lo, state]`），而非单字节
  - opcode 使用 `0xC7`（Vendor Model OnOff），akf=1（AppKey 加密）
  - 亮度控制 opcode `0xC4`，色温控制 opcode `0xC6` 均改用 Vendor Model 格式
- **验证**：灯带后（设备 4106713320016256）开/关控制成功

#### 2. 多区域开关控制修复

- **问题**：多区域开关控制不生效，且操作一个区域时其他区域状态也被错误更新
- **修复**：
  - `set_device_on()` 新增 `zone` 参数，zone_bitmask = `1 << (zone_index - 1)`
  - 新增 `_update_zone_state()` 方法：多区域开关只更新 `reportinstruct_status_byte` 的对应 bit，不影响其他 zone
  - switch.py 多区域控制从旧的云 API 格式（`66BB...`）改为 Vendor Model OnOff
- **验证**：餐桌旁三区域开关（设备 4106714948321344）zone 1 独立控制成功，zone 2/3 不受影响

#### 3. Mesh 源地址修复

- **问题**：本地源地址硬编码为 `0x0001`，设备可能因源地址不匹配而静默丢弃消息
- **修复**：从 API `provisioneraddress` 字段获取 provisioner 的 unicast 地址并传给 `mesh_manager.set_local_address()`

#### 4. BLE 连接槽位泄漏修复

- **问题**：Mesh 连接尝试失败后泄漏 BLE 槽位，5 个槽位逐渐被占满，最终导致 Mesh 无法连接
- **修复**：
  - 限制候选设备数量为 2 个（之前 10 个）
  - 连接失败后调用 `close_stale_connections()` 清理泄漏的连接
  - `disconnect()` 无条件断开 client，不再依赖 `is_connected` 状态

#### 5. Mesh 连接稳定性

- Mesh 连接超时 90 秒（`asyncio.wait_for`），防止阻塞 HA 启动
- Mesh 初始化使用 `api.place_id` 而非解析 `coordinator.places`
- NID 验证：已知 Ltech MAC 跳过验证以加速连接

### 控制优先级

```
Mesh Vendor Model (本地蓝牙) → 云端 API → MQTT
```

- Mesh 可用时优先使用 Vendor Model OnOff（opcode 0xC7, akf=1, AppKey）
- Mesh 不可用时回退到云端 API + MQTT
- 控制后 15 秒状态保护期，防止同步覆盖本地设置的状态

### 涉及文件

| 文件 | 修改内容 |
|------|----------|
| `mesh_crypto.py` | company_id 修正、Vendor Model 消息格式标准化 |
| `mesh_manager.py` | zone 参数、BLE 槽位泄漏修复、源地址设置 |
| `coordinator.py` | provisioner 地址捕获、zone 参数传递、Mesh 超时 |
| `switch.py` | Vendor Model 控制、zone 状态独立管理 |
| `light.py` | Vendor Model 控制参数格式修正 |

---

## Features

- **Light Control**: Support for dimmable lights, color temperature lights, and RGB lights
- **Switch Control**: Support for smart switches, relays, and sockets
- **Sensor Support**: Temperature, humidity, motion, door/window, and battery sensors
- **Automatic Device Discovery**: Automatically discovers all devices associated with your Ltech account
- **Cloud Polling**: Periodically syncs device states from Ltech cloud
- **Session Management**: Automatic re-authentication when session expires

## Supported Devices

### Lights
- LIGHT_CT (Color Temperature)
- LIGHT_DIM (Dimmable)
- LIGHT_COLOR (RGB)
- LIGHT_DUV
- LIGHT_512
- CGD_PRO_LIGHT
- DALI_LIGHT

### Switches
- SWITCH_1G ~ SWITCH_4G
- RELAY_1CH ~ RELAY_4CH
- SOCKET

### Sensors
- HSDSENSOR_TEMP (Temperature)
- HSDSENSOR_HUMI (Humidity)
- HSDSENSOR_PIR (Motion)
- HSDSENSOR_DOOR (Door/Window)
- HSDSENSOR_BATTERY (Battery)

## Installation

### Manual Installation

1. Download the [latest release](https://github.com/ltech/ltech-hass-integration/releases)
2. Extract the `custom_components/ltech` folder to your Home Assistant `custom_components` directory
3. Restart Home Assistant

### HACS Installation (Recommended)

This integration is not yet available in HACS. Please use manual installation for now.

## Configuration

1. Go to **Settings > Devices & Services**
2. Click **Add Integration**
3. Search for **Ltech Smart Home**
4. Enter your Ltech account credentials:
   - **Email**: Your Ltech account email
   - **Password**: Your Ltech account password
   - **Server URL**: 
     - China: `https://apic.ltsys.com.cn:2443/` (default)
     - Overseas: Contact Ltech support for the correct URL
5. Click **Submit**

## Usage

Once configured, your Ltech devices will appear in Home Assistant. You can:

- Control lights (on/off, brightness, color temperature)
- Control switches (on/off)
- View sensor readings (temperature, humidity, motion, etc.)

### Example Automations

```yaml
# Turn on living room light at sunset
automation:
  - alias: "Living Room Light On at Sunset"
    trigger:
      platform: sun
      event: sunset
    action:
      service: light.turn_on
      target:
        entity_id: light.living_room_main_light

# Turn off all lights when leaving home
  - alias: "Turn Off All Lights When Leaving"
    trigger:
      platform: zone
      entity_id: person.home
      zone: zone.home
      event: leave
    action:
      service: light.turn_off
      target:
        entity_id: all
```

## API Documentation

This integration uses the Ltech REST API for communication:

- Base URL: `https://apic.ltsys.com.cn:2443/openapi/rest`
- Authentication: AES-ECB encryption + MD5 signature
- Session-based authentication

## Troubleshooting

### Common Issues

1. **Connection Failed**: Ensure your email and password are correct. Check network connectivity.
2. **Devices Not Showing Up**: Make sure devices are online in the Ltech app first.
3. **Control Not Working**: Verify the device is online and you have permission to control it.

### Logs

To enable debug logging, add the following to your `configuration.yaml`:

```yaml
logger:
  default: info
  logs:
    custom_components.ltech: debug
```

## Support

For support, please:
1. Check the [Home Assistant Community Forum](https://community.home-assistant.io/)
2. Open an issue on the GitHub repository

## License

This integration is licensed under the MIT License.

## Disclaimer

This integration is not officially supported by Ltech. Use at your own risk.