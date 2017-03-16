// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: merge.proto at 390:1
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
import java.util.List;
import okio.ByteString;

public final class PBTrdGetRsp extends Message<PBTrdGetRsp, PBTrdGetRsp.Builder> {
  public static final ProtoAdapter<PBTrdGetRsp> ADAPTER = new ProtoAdapter_PBTrdGetRsp();

  private static final long serialVersionUID = 0L;

  public static final Integer DEFAULT_RESULT = 0;

  @WireField(
      tag = 1,
      adapter = "com.squareup.wire.ProtoAdapter#INT32"
  )
  public final Integer result;

  @WireField(
      tag = 2,
      adapter = "org.yeewoe.mopanet.protos.PBTrends#ADAPTER",
      label = WireField.Label.REPEATED
  )
  public final List<PBTrends> trends;

  public PBTrdGetRsp(Integer result, List<PBTrends> trends) {
    this(result, trends, ByteString.EMPTY);
  }

  public PBTrdGetRsp(Integer result, List<PBTrends> trends, ByteString unknownFields) {
    super(ADAPTER, unknownFields);
    this.result = result;
    this.trends = Internal.immutableCopyOf("trends", trends);
  }

  @Override
  public Builder newBuilder() {
    Builder builder = new Builder();
    builder.result = result;
    builder.trends = Internal.copyOf("trends", trends);
    builder.addUnknownFields(unknownFields());
    return builder;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof PBTrdGetRsp)) return false;
    PBTrdGetRsp o = (PBTrdGetRsp) other;
    return Internal.equals(unknownFields(), o.unknownFields())
        && Internal.equals(result, o.result)
        && Internal.equals(trends, o.trends);
  }

  @Override
  public int hashCode() {
    int result_ = super.hashCode;
    if (result_ == 0) {
      result_ = unknownFields().hashCode();
      result_ = result_ * 37 + (result != null ? result.hashCode() : 0);
      result_ = result_ * 37 + (trends != null ? trends.hashCode() : 1);
      super.hashCode = result_;
    }
    return result_;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (result != null) builder.append(", result=").append(result);
    if (trends != null) builder.append(", trends=").append(trends);
    return builder.replace(0, 2, "PBTrdGetRsp{").append('}').toString();
  }

  public static final class Builder extends Message.Builder<PBTrdGetRsp, Builder> {
    public Integer result;

    public List<PBTrends> trends;

    public Builder() {
      trends = Internal.newMutableList();
    }

    public Builder result(Integer result) {
      this.result = result;
      return this;
    }

    public Builder trends(List<PBTrends> trends) {
      Internal.checkElementsNotNull(trends);
      this.trends = trends;
      return this;
    }

    @Override
    public PBTrdGetRsp build() {
      return new PBTrdGetRsp(result, trends, buildUnknownFields());
    }
  }

  private static final class ProtoAdapter_PBTrdGetRsp extends ProtoAdapter<PBTrdGetRsp> {
    ProtoAdapter_PBTrdGetRsp() {
      super(FieldEncoding.LENGTH_DELIMITED, PBTrdGetRsp.class);
    }

    @Override
    public int encodedSize(PBTrdGetRsp value) {
      return (value.result != null ? ProtoAdapter.INT32.encodedSizeWithTag(1, value.result) : 0)
          + PBTrends.ADAPTER.asRepeated().encodedSizeWithTag(2, value.trends)
          + value.unknownFields().size();
    }

    @Override
    public void encode(ProtoWriter writer, PBTrdGetRsp value) throws IOException {
      if (value.result != null) ProtoAdapter.INT32.encodeWithTag(writer, 1, value.result);
      if (value.trends != null) PBTrends.ADAPTER.asRepeated().encodeWithTag(writer, 2, value.trends);
      writer.writeBytes(value.unknownFields());
    }

    @Override
    public PBTrdGetRsp decode(ProtoReader reader) throws IOException {
      Builder builder = new Builder();
      long token = reader.beginMessage();
      for (int tag; (tag = reader.nextTag()) != -1;) {
        switch (tag) {
          case 1: builder.result(ProtoAdapter.INT32.decode(reader)); break;
          case 2: builder.trends.add(PBTrends.ADAPTER.decode(reader)); break;
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
    public PBTrdGetRsp redact(PBTrdGetRsp value) {
      Builder builder = value.newBuilder();
      Internal.redactElements(builder.trends, PBTrends.ADAPTER);
      builder.clearUnknownFields();
      return builder.build();
    }
  }
}