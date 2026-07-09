package com.ltech.smarthome.adapter;

import android.util.ArrayMap;
import androidx.exifinterface.media.ExifInterface;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class ArrayMapTypeAdapter extends TypeAdapter<Object> {
    @Override // com.google.gson.TypeAdapter
    public void write(JsonWriter out, Object value) throws IOException {
    }

    /* renamed from: com.ltech.smarthome.adapter.ArrayMapTypeAdapter$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$gson$stream$JsonToken;

        static {
            int[] iArr = new int[JsonToken.values().length];
            $SwitchMap$com$google$gson$stream$JsonToken = iArr;
            try {
                iArr[JsonToken.BEGIN_ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.BEGIN_OBJECT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.STRING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.NUMBER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.BOOLEAN.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.NULL.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    @Override // com.google.gson.TypeAdapter
    public Object read(JsonReader in) throws IOException {
        switch (AnonymousClass1.$SwitchMap$com$google$gson$stream$JsonToken[in.peek().ordinal()]) {
            case 1:
                ArrayList arrayList = new ArrayList();
                in.beginArray();
                while (in.hasNext()) {
                    arrayList.add(read(in));
                }
                in.endArray();
                return arrayList;
            case 2:
                ArrayMap arrayMap = new ArrayMap();
                in.beginObject();
                while (in.hasNext()) {
                    arrayMap.put(in.nextName(), read(in));
                }
                in.endObject();
                return arrayMap;
            case 3:
                return in.nextString();
            case 4:
                String nextString = in.nextString();
                if (nextString.contains(".") || nextString.contains("e") || nextString.contains(ExifInterface.LONGITUDE_EAST)) {
                    return Double.valueOf(Double.parseDouble(nextString));
                }
                if (Long.parseLong(nextString) <= 2147483647L) {
                    return Integer.valueOf(Integer.parseInt(nextString));
                }
                return Long.valueOf(Long.parseLong(nextString));
            case 5:
                return Boolean.valueOf(in.nextBoolean());
            case 6:
                in.nextNull();
                return null;
            default:
                throw new IllegalStateException();
        }
    }
}