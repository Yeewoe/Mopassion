// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: merge.proto at 447:1
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
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.util.List;
import okio.ByteString;

public final class PBTrdNearRsp extends Message<PBTrdNearRsp, PBTrdNearRsp.Builder> {
  public static final ProtoAdapter<PBTrdNearRsp> ADAPTER = new ProtoAdapter_PBTrdNearRsp();

  private static final long serialVersionUID = 0L;

  public static final Integer DEFAULT_RESULT = 0;

  @WireField(
      tag = 1,
      adapter = "com.squareup.wire.ProtoAdapter#INT32"
  )
  public final Integer result;

  @WireField(
      tag = 2,
      adapter = "com.squareup.wire.ProtoAdapter#UINT64",
      label = WireField.Label.REPEATED
  )
  public final List<Long> ids;

  public PBTrdNearRsp(Integer result, List<Long> ids) {
    this(result, ids, ByteString.EMPTY);
  }

  public PBTrdNearRsp(Integer result, List<Long> ids, ByteString unknownFields) {
    super(ADAPTER, unknownFields);
    this.result = result;
    this.ids = Internal.immutableCopyOf("ids", ids);
  }

  @Override
  public Builder newBuilder() {
    Builder builder = new Builder();
    builder.result = result;
    builder.ids = Internal.copyOf("ids", ids);
    builder.addUnknownFields(unknownFields());
    return builder;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof PBTrdNearRsp)) return false;
    PBTrdNearRsp o = (PBTrdNearRsp) other;
    return Internal.equals(unknownFields(), o.unknownFields())
        && Internal.equals(result, o.result)
        && Internal.equals(ids, o.ids);
  }

  @Override
  public int hashCode() {
    int result_ = super.hashCode;
    if (result_ == 0) {
      result_ = unknownFields().hashCode();
      result_ = result_ * 37 + (result != null ? result.hashCode() : 0);
      result_ = result_ * 37 + (ids != null ? ids.hashCode() : 1);
      super.hashCode = result_;
    }
    return result_;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (result != null) builder.append(", result=").append(result);
    if (ids != null) builder.append(", ids=").append(ids);
    return builder.replace(0, 2, "PBTrdNearRsp{").append('}').toString();
  }

  public static final class Builder extends Message.Builder<PBTrdNearRsp, Builder> {
    public Integer result;

    public List<Long> ids;

    public Builder() {
      ids = Internal.newMutableList();
    }

    public Builder result(Integer result) {
      this.result = result;
      return this;
    }

    public Builder ids(List<Long> ids) {
      Internal.checkElementsNotNull(ids);
      this.ids = ids;
      return this;
    }

    @Override
    public PBTrdNearRsp build() {
      return new PBTrdNearRsp(result, ids, buildUnknownFields());
    }
  }

  private static final class ProtoAdapter_PBTrdNearRsp extends ProtoAdapter<PBTrdNearRsp> {
    ProtoAdapter_PBTrdNearRsp() {
      super(FieldEncoding.LENGTH_DELIMITED, PBTrdNearRsp.class);
    }

    @Override
    public int encodedSize(PBTrdNearRsp value) {
      return (value.result != null ? ProtoAdapter.INT32.encodedSizeWithTag(1, value.result) : 0)
          + ProtoAdapter.UINT64.asRepeated().encodedSizeWithTag(2, value.ids)
          + value.unknownFields().size();
    }

    @Override
    public void encode(ProtoWriter writer, PBTrdNearRsp value) throws IOException {
      if (value.result != null) ProtoAdapter.INT32.encodeWithTag(writer, 1, value.result);
      if (value.ids != null) ProtoAdapter.UINT64.asRepeated().encodeWithTag(writer, 2, value.ids);
      writer.writeBytes(value.unknownFields());
    }

    @Override
    public PBTrdNearRsp decode(ProtoReader reader) throws IOException {
      Builder builder = new Builder();
      long token = reader.beginMessage();
      for (int tag; (tag = reader.nextTag()) != -1;) {
        switch (tag) {
          case 1: builder.result(ProtoAdapter.INT32.decode(reader)); break;
          case 2: builder.ids.add(ProtoAdapter.UINT64.decode(reader)); break;
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
    public PBTrdNearRsp redact(PBTrdNearRsp value) {
      Builder builder = value.newBuilder();
      builder.clearUnknownFields();
      return builder.build();
    }
  }
}
