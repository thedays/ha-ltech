# Ltech Smart Home Integration for Home Assistant

This integration allows you to control Ltech smart home devices through Home Assistant.

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