// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: merge.proto at 461:1
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

public final class PBTrdLikeRsp extends Message<PBTrdLikeRsp, PBTrdLikeRsp.Builder> {
  public static final ProtoAdapter<PBTrdLikeRsp> ADAPTER = new ProtoAdapter_PBTrdLikeRsp();

  private static final long serialVersionUID = 0L;

  public static final Integer DEFAULT_RESULT = 0;

  /**
   * ���ؽ����0��ձ�ʾ�ɹ�
   */
  @WireField(
      tag = 1,
      adapter = "com.squareup.wire.ProtoAdapter#INT32"
  )
  public final Integer result;

  public PBTrdLikeRsp(Integer result) {
    this(result, ByteString.EMPTY);
  }

  public PBTrdLikeRsp(Integer result, ByteString unknownFields) {
    super(ADAPTER, unknownFields);
    this.result = result;
  }

  @Override
  public Builder newBuilder() {
    Builder builder = new Builder();
    builder.result = result;
    builder.addUnknownFields(unknownFields());
    return builder;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof PBTrdLikeRsp)) return false;
    PBTrdLikeRsp o = (PBTrdLikeRsp) other;
    return Internal.equals(unknownFields(), o.unknownFields())
        && Internal.equals(result, o.result);
  }

  @Override
  public int hashCode() {
    int result_ = super.hashCode;
    if (result_ == 0) {
      result_ = unknownFields().hashCode();
      result_ = result_ * 37 + (result != null ? result.hashCode() : 0);
      super.hashCode = result_;
    }
    return result_;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (result != null) builder.append(", result=").append(result);
    return builder.replace(0, 2, "PBTrdLikeRsp{").append('}').toString();
  }

  public static final class Builder extends Message.Builder<PBTrdLikeRsp, Builder> {
    public Integer result;

    public Builder() {
    }

    /**
     * ���ؽ����0��ձ�ʾ�ɹ�
     */
    public Builder result(Integer result) {
      this.result = result;
      return this;
    }

    @Override
    public PBTrdLikeRsp build() {
      return new PBTrdLikeRsp(result, buildUnknownFields());
    }
  }

  private static final class ProtoAdapter_PBTrdLikeRsp extends ProtoAdapter<PBTrdLikeRsp> {
    ProtoAdapter_PBTrdLikeRsp() {
      super(FieldEncoding.LENGTH_DELIMITED, PBTrdLikeRsp.class);
    }

    @Override
    public int encodedSize(PBTrdLikeRsp value) {
      return (value.result != null ? ProtoAdapter.INT32.encodedSizeWithTag(1, value.result) : 0)
          + value.unknownFields().size();
    }

    @Override
    public void encode(ProtoWriter writer, PBTrdLikeRsp value) throws IOException {
      if (value.result != null) ProtoAdapter.INT32.encodeWithTag(writer, 1, value.result);
      writer.writeBytes(value.unknownFields());
    }

    @Override
    public PBTrdLikeRsp decode(ProtoReader reader) throws IOException {
      Builder builder = new Builder();
      long token = reader.beginMessage();
      for (int tag; (tag = reader.nextTag()) != -1;) {
        switch (tag) {
          case 1: builder.result(ProtoAdapter.INT32.decode(reader)); break;
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
    public PBTrdLikeRsp redact(PBTrdLikeRsp value) {
      Builder builder = value.newBuilder();
      builder.clearUnknownFields();
      return builder.build();
    }
  }
}
