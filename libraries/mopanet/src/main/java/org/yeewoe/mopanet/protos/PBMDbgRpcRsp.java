// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: merge.proto at 272:1
package org.yeewoe.mopanet.protos;

import com.squareup.wire.FieldEncoding;
import com.squareup.wire.Message;
import com.squareup.wire.ProtoAdapter;
import com.squareup.wire.ProtoReader;
import com.squareup.wire.ProtoWriter;
import com.squareup.wire.WireField;
import com.squareup.wire.internal.Internal;
import java.io.IOException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import okio.ByteString;

public final class PBMDbgRpcRsp extends Message<PBMDbgRpcRsp, PBMDbgRpcRsp.Builder> {
  public static final ProtoAdapter<PBMDbgRpcRsp> ADAPTER = new ProtoAdapter_PBMDbgRpcRsp();

  private static final long serialVersionUID = 0L;

  public static final Integer DEFAULT_RESULT = 0;

  public static final String DEFAULT_CMDRSP = "";

  @WireField(
      tag = 1,
      adapter = "com.squareup.wire.ProtoAdapter#INT32"
  )
  public final Integer result;

  @WireField(
      tag = 3,
      adapter = "com.squareup.wire.ProtoAdapter#STRING"
  )
  public final String cmdrsp;

  public PBMDbgRpcRsp(Integer result, String cmdrsp) {
    this(result, cmdrsp, ByteString.EMPTY);
  }

  public PBMDbgRpcRsp(Integer result, String cmdrsp, ByteString unknownFields) {
    super(ADAPTER, unknownFields);
    this.result = result;
    this.cmdrsp = cmdrsp;
  }

  @Override
  public Builder newBuilder() {
    Builder builder = new Builder();
    builder.result = result;
    builder.cmdrsp = cmdrsp;
    builder.addUnknownFields(unknownFields());
    return builder;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof PBMDbgRpcRsp)) return false;
    PBMDbgRpcRsp o = (PBMDbgRpcRsp) other;
    return Internal.equals(unknownFields(), o.unknownFields())
        && Internal.equals(result, o.result)
        && Internal.equals(cmdrsp, o.cmdrsp);
  }

  @Override
  public int hashCode() {
    int result_ = super.hashCode;
    if (result_ == 0) {
      result_ = unknownFields().hashCode();
      result_ = result_ * 37 + (result != null ? result.hashCode() : 0);
      result_ = result_ * 37 + (cmdrsp != null ? cmdrsp.hashCode() : 0);
      super.hashCode = result_;
    }
    return result_;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (result != null) builder.append(", result=").append(result);
    if (cmdrsp != null) builder.append(", cmdrsp=").append(cmdrsp);
    return builder.replace(0, 2, "PBMDbgRpcRsp{").append('}').toString();
  }

  public static final class Builder extends Message.Builder<PBMDbgRpcRsp, Builder> {
    public Integer result;

    public String cmdrsp;

    public Builder() {
    }

    public Builder result(Integer result) {
      this.result = result;
      return this;
    }

    public Builder cmdrsp(String cmdrsp) {
      this.cmdrsp = cmdrsp;
      return this;
    }

    @Override
    public PBMDbgRpcRsp build() {
      return new PBMDbgRpcRsp(result, cmdrsp, buildUnknownFields());
    }
  }

  private static final class ProtoAdapter_PBMDbgRpcRsp extends ProtoAdapter<PBMDbgRpcRsp> {
    ProtoAdapter_PBMDbgRpcRsp() {
      super(FieldEncoding.LENGTH_DELIMITED, PBMDbgRpcRsp.class);
    }

    @Override
    public int encodedSize(PBMDbgRpcRsp value) {
      return (value.result != null ? ProtoAdapter.INT32.encodedSizeWithTag(1, value.result) : 0)
          + (value.cmdrsp != null ? ProtoAdapter.STRING.encodedSizeWithTag(3, value.cmdrsp) : 0)
          + value.unknownFields().size();
    }

    @Override
    public void encode(ProtoWriter writer, PBMDbgRpcRsp value) throws IOException {
      if (value.result != null) ProtoAdapter.INT32.encodeWithTag(writer, 1, value.result);
      if (value.cmdrsp != null) ProtoAdapter.STRING.encodeWithTag(writer, 3, value.cmdrsp);
      writer.writeBytes(value.unknownFields());
    }

    @Override
    public PBMDbgRpcRsp decode(ProtoReader reader) throws IOException {
      Builder builder = new Builder();
      long token = reader.beginMessage();
      for (int tag; (tag = reader.nextTag()) != -1;) {
        switch (tag) {
          case 1: builder.result(ProtoAdapter.INT32.decode(reader)); break;
          case 3: builder.cmdrsp(ProtoAdapter.STRING.decode(reader)); break;
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
    public PBMDbgRpcRsp redact(PBMDbgRpcRsp value) {
      Builder builder = value.newBuilder();
      builder.clearUnknownFields();
      return builder.build();
    }
  }
}