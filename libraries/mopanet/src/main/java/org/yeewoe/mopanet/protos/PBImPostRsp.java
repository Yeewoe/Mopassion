// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: merge.proto at 219:1
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
import okio.ByteString;

public final class PBImPostRsp extends Message<PBImPostRsp, PBImPostRsp.Builder> {
  public static final ProtoAdapter<PBImPostRsp> ADAPTER = new ProtoAdapter_PBImPostRsp();

  private static final long serialVersionUID = 0L;

  public static final Integer DEFAULT_RESULT = 0;

  public static final Long DEFAULT_ACK_IMSGID = 0L;

  @WireField(
      tag = 1,
      adapter = "com.squareup.wire.ProtoAdapter#INT32"
  )
  public final Integer result;

  /**
   * 	�Ѿ�ȷ�Ͻ��ܵ���ϢID��ֵΪPBImPostReq.imsg.imsgid
   */
  @WireField(
      tag = 2,
      adapter = "com.squareup.wire.ProtoAdapter#UINT64"
  )
  public final Long ack_imsgid;

  public PBImPostRsp(Integer result, Long ack_imsgid) {
    this(result, ack_imsgid, ByteString.EMPTY);
  }

  public PBImPostRsp(Integer result, Long ack_imsgid, ByteString unknownFields) {
    super(ADAPTER, unknownFields);
    this.result = result;
    this.ack_imsgid = ack_imsgid;
  }

  @Override
  public Builder newBuilder() {
    Builder builder = new Builder();
    builder.result = result;
    builder.ack_imsgid = ack_imsgid;
    builder.addUnknownFields(unknownFields());
    return builder;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof PBImPostRsp)) return false;
    PBImPostRsp o = (PBImPostRsp) other;
    return Internal.equals(unknownFields(), o.unknownFields())
        && Internal.equals(result, o.result)
        && Internal.equals(ack_imsgid, o.ack_imsgid);
  }

  @Override
  public int hashCode() {
    int result_ = super.hashCode;
    if (result_ == 0) {
      result_ = unknownFields().hashCode();
      result_ = result_ * 37 + (result != null ? result.hashCode() : 0);
      result_ = result_ * 37 + (ack_imsgid != null ? ack_imsgid.hashCode() : 0);
      super.hashCode = result_;
    }
    return result_;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (result != null) builder.append(", result=").append(result);
    if (ack_imsgid != null) builder.append(", ack_imsgid=").append(ack_imsgid);
    return builder.replace(0, 2, "PBImPostRsp{").append('}').toString();
  }

  public static final class Builder extends Message.Builder<PBImPostRsp, Builder> {
    public Integer result;

    public Long ack_imsgid;

    public Builder() {
    }

    public Builder result(Integer result) {
      this.result = result;
      return this;
    }

    /**
     * 	�Ѿ�ȷ�Ͻ��ܵ���ϢID��ֵΪPBImPostReq.imsg.imsgid
     */
    public Builder ack_imsgid(Long ack_imsgid) {
      this.ack_imsgid = ack_imsgid;
      return this;
    }

    @Override
    public PBImPostRsp build() {
      return new PBImPostRsp(result, ack_imsgid, buildUnknownFields());
    }
  }

  private static final class ProtoAdapter_PBImPostRsp extends ProtoAdapter<PBImPostRsp> {
    ProtoAdapter_PBImPostRsp() {
      super(FieldEncoding.LENGTH_DELIMITED, PBImPostRsp.class);
    }

    @Override
    public int encodedSize(PBImPostRsp value) {
      return (value.result != null ? ProtoAdapter.INT32.encodedSizeWithTag(1, value.result) : 0)
          + (value.ack_imsgid != null ? ProtoAdapter.UINT64.encodedSizeWithTag(2, value.ack_imsgid) : 0)
          + value.unknownFields().size();
    }

    @Override
    public void encode(ProtoWriter writer, PBImPostRsp value) throws IOException {
      if (value.result != null) ProtoAdapter.INT32.encodeWithTag(writer, 1, value.result);
      if (value.ack_imsgid != null) ProtoAdapter.UINT64.encodeWithTag(writer, 2, value.ack_imsgid);
      writer.writeBytes(value.unknownFields());
    }

    @Override
    public PBImPostRsp decode(ProtoReader reader) throws IOException {
      Builder builder = new Builder();
      long token = reader.beginMessage();
      for (int tag; (tag = reader.nextTag()) != -1;) {
        switch (tag) {
          case 1: builder.result(ProtoAdapter.INT32.decode(reader)); break;
          case 2: builder.ack_imsgid(ProtoAdapter.UINT64.decode(reader)); break;
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
    public PBImPostRsp redact(PBImPostRsp value) {
      Builder builder = value.newBuilder();
      builder.clearUnknownFields();
      return builder.build();
    }
  }
}