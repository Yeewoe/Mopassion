// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: merge.proto at 675:1
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

public final class PBUsrFollowCountRsp extends Message<PBUsrFollowCountRsp, PBUsrFollowCountRsp.Builder> {
  public static final ProtoAdapter<PBUsrFollowCountRsp> ADAPTER = new ProtoAdapter_PBUsrFollowCountRsp();

  private static final long serialVersionUID = 0L;

  public static final Integer DEFAULT_RESULT = 0;

  public static final Integer DEFAULT_COUNT = 0;

  @WireField(
      tag = 1,
      adapter = "com.squareup.wire.ProtoAdapter#INT32"
  )
  public final Integer result;

  /**
   * ��ע�˵�����
   */
  @WireField(
      tag = 2,
      adapter = "com.squareup.wire.ProtoAdapter#INT32"
  )
  public final Integer count;

  public PBUsrFollowCountRsp(Integer result, Integer count) {
    this(result, count, ByteString.EMPTY);
  }

  public PBUsrFollowCountRsp(Integer result, Integer count, ByteString unknownFields) {
    super(ADAPTER, unknownFields);
    this.result = result;
    this.count = count;
  }

  @Override
  public Builder newBuilder() {
    Builder builder = new Builder();
    builder.result = result;
    builder.count = count;
    builder.addUnknownFields(unknownFields());
    return builder;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof PBUsrFollowCountRsp)) return false;
    PBUsrFollowCountRsp o = (PBUsrFollowCountRsp) other;
    return Internal.equals(unknownFields(), o.unknownFields())
        && Internal.equals(result, o.result)
        && Internal.equals(count, o.count);
  }

  @Override
  public int hashCode() {
    int result_ = super.hashCode;
    if (result_ == 0) {
      result_ = unknownFields().hashCode();
      result_ = result_ * 37 + (result != null ? result.hashCode() : 0);
      result_ = result_ * 37 + (count != null ? count.hashCode() : 0);
      super.hashCode = result_;
    }
    return result_;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (result != null) builder.append(", result=").append(result);
    if (count != null) builder.append(", count=").append(count);
    return builder.replace(0, 2, "PBUsrFollowCountRsp{").append('}').toString();
  }

  public static final class Builder extends Message.Builder<PBUsrFollowCountRsp, Builder> {
    public Integer result;

    public Integer count;

    public Builder() {
    }

    public Builder result(Integer result) {
      this.result = result;
      return this;
    }

    /**
     * ��ע�˵�����
     */
    public Builder count(Integer count) {
      this.count = count;
      return this;
    }

    @Override
    public PBUsrFollowCountRsp build() {
      return new PBUsrFollowCountRsp(result, count, buildUnknownFields());
    }
  }

  private static final class ProtoAdapter_PBUsrFollowCountRsp extends ProtoAdapter<PBUsrFollowCountRsp> {
    ProtoAdapter_PBUsrFollowCountRsp() {
      super(FieldEncoding.LENGTH_DELIMITED, PBUsrFollowCountRsp.class);
    }

    @Override
    public int encodedSize(PBUsrFollowCountRsp value) {
      return (value.result != null ? ProtoAdapter.INT32.encodedSizeWithTag(1, value.result) : 0)
          + (value.count != null ? ProtoAdapter.INT32.encodedSizeWithTag(2, value.count) : 0)
          + value.unknownFields().size();
    }

    @Override
    public void encode(ProtoWriter writer, PBUsrFollowCountRsp value) throws IOException {
      if (value.result != null) ProtoAdapter.INT32.encodeWithTag(writer, 1, value.result);
      if (value.count != null) ProtoAdapter.INT32.encodeWithTag(writer, 2, value.count);
      writer.writeBytes(value.unknownFields());
    }

    @Override
    public PBUsrFollowCountRsp decode(ProtoReader reader) throws IOException {
      Builder builder = new Builder();
      long token = reader.beginMessage();
      for (int tag; (tag = reader.nextTag()) != -1;) {
        switch (tag) {
          case 1: builder.result(ProtoAdapter.INT32.decode(reader)); break;
          case 2: builder.count(ProtoAdapter.INT32.decode(reader)); break;
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
    public PBUsrFollowCountRsp redact(PBUsrFollowCountRsp value) {
      Builder builder = value.newBuilder();
      builder.clearUnknownFields();
      return builder.build();
    }
  }
}