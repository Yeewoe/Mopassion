// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: merge.proto at 24:1
package org.yeewoe.mopanet.protos;

import com.squareup.wire.FieldEncoding;
import com.squareup.wire.Message;
import com.squareup.wire.ProtoAdapter;
import com.squareup.wire.ProtoReader;
import com.squareup.wire.ProtoWriter;
import com.squareup.wire.WireField;
import com.squareup.wire.internal.Internal;
import java.io.IOException;
import java.lang.Double;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import okio.ByteString;

/**
 * ????��??
 */
public final class PBMapPostion extends Message<PBMapPostion, PBMapPostion.Builder> {
  public static final ProtoAdapter<PBMapPostion> ADAPTER = new ProtoAdapter_PBMapPostion();

  private static final long serialVersionUID = 0L;

  public static final String DEFAULT_ADDR = "";

  public static final Double DEFAULT_LON = 0.0d;

  public static final Double DEFAULT_LAT = 0.0d;

  /**
   * ??????
   */
  @WireField(
      tag = 1,
      adapter = "com.squareup.wire.ProtoAdapter#STRING"
  )
  public final String addr;

  /**
   * ????
   */
  @WireField(
      tag = 2,
      adapter = "com.squareup.wire.ProtoAdapter#DOUBLE"
  )
  public final Double lon;

  /**
   * ��??
   */
  @WireField(
      tag = 3,
      adapter = "com.squareup.wire.ProtoAdapter#DOUBLE"
  )
  public final Double lat;

  public PBMapPostion(String addr, Double lon, Double lat) {
    this(addr, lon, lat, ByteString.EMPTY);
  }

  public PBMapPostion(String addr, Double lon, Double lat, ByteString unknownFields) {
    super(ADAPTER, unknownFields);
    this.addr = addr;
    this.lon = lon;
    this.lat = lat;
  }

  @Override
  public Builder newBuilder() {
    Builder builder = new Builder();
    builder.addr = addr;
    builder.lon = lon;
    builder.lat = lat;
    builder.addUnknownFields(unknownFields());
    return builder;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof PBMapPostion)) return false;
    PBMapPostion o = (PBMapPostion) other;
    return Internal.equals(unknownFields(), o.unknownFields())
        && Internal.equals(addr, o.addr)
        && Internal.equals(lon, o.lon)
        && Internal.equals(lat, o.lat);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode;
    if (result == 0) {
      result = unknownFields().hashCode();
      result = result * 37 + (addr != null ? addr.hashCode() : 0);
      result = result * 37 + (lon != null ? lon.hashCode() : 0);
      result = result * 37 + (lat != null ? lat.hashCode() : 0);
      super.hashCode = result;
    }
    return result;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (addr != null) builder.append(", addr=").append(addr);
    if (lon != null) builder.append(", lon=").append(lon);
    if (lat != null) builder.append(", lat=").append(lat);
    return builder.replace(0, 2, "PBMapPostion{").append('}').toString();
  }

  public static final class Builder extends Message.Builder<PBMapPostion, Builder> {
    public String addr;

    public Double lon;

    public Double lat;

    public Builder() {
    }

    /**
     * ??????
     */
    public Builder addr(String addr) {
      this.addr = addr;
      return this;
    }

    /**
     * ????
     */
    public Builder lon(Double lon) {
      this.lon = lon;
      return this;
    }

    /**
     * ��??
     */
    public Builder lat(Double lat) {
      this.lat = lat;
      return this;
    }

    @Override
    public PBMapPostion build() {
      return new PBMapPostion(addr, lon, lat, buildUnknownFields());
    }
  }

  private static final class ProtoAdapter_PBMapPostion extends ProtoAdapter<PBMapPostion> {
    ProtoAdapter_PBMapPostion() {
      super(FieldEncoding.LENGTH_DELIMITED, PBMapPostion.class);
    }

    @Override
    public int encodedSize(PBMapPostion value) {
      return (value.addr != null ? ProtoAdapter.STRING.encodedSizeWithTag(1, value.addr) : 0)
          + (value.lon != null ? ProtoAdapter.DOUBLE.encodedSizeWithTag(2, value.lon) : 0)
          + (value.lat != null ? ProtoAdapter.DOUBLE.encodedSizeWithTag(3, value.lat) : 0)
          + value.unknownFields().size();
    }

    @Override
    public void encode(ProtoWriter writer, PBMapPostion value) throws IOException {
      if (value.addr != null) ProtoAdapter.STRING.encodeWithTag(writer, 1, value.addr);
      if (value.lon != null) ProtoAdapter.DOUBLE.encodeWithTag(writer, 2, value.lon);
      if (value.lat != null) ProtoAdapter.DOUBLE.encodeWithTag(writer, 3, value.lat);
      writer.writeBytes(value.unknownFields());
    }

    @Override
    public PBMapPostion decode(ProtoReader reader) throws IOException {
      Builder builder = new Builder();
      long token = reader.beginMessage();
      for (int tag; (tag = reader.nextTag()) != -1;) {
        switch (tag) {
          case 1: builder.addr(ProtoAdapter.STRING.decode(reader)); break;
          case 2: builder.lon(ProtoAdapter.DOUBLE.decode(reader)); break;
          case 3: builder.lat(ProtoAdapter.DOUBLE.decode(reader)); break;
          default: {
            FieldEncoding fieldEncoding = reader.peekFieldEncoding();
            Object value = fieldEncoding.rawProtoAdapter().decode(reader);
            builder.addUnknownField(tag, fieldEncoding, value);
          }
        }
      }
      reader.endMessage(token);
      return builder.build();
    }

    @Override
    public PBMapPostion redact(PBMapPostion value) {
      Builder builder = value.newBuilder();
      builder.clearUnknownFields();
      return builder.build();
    }
  }
}