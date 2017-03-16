// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: merge.proto at 681:1
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

/**
 * ��ȡ�����б�
 */
public final class PBUsrFriendListReq extends Message<PBUsrFriendListReq, PBUsrFriendListReq.Builder> {
  public static final ProtoAdapter<PBUsrFriendListReq> ADAPTER = new ProtoAdapter_PBUsrFriendListReq();

  private static final long serialVersionUID = 0L;

  public static final Integer DEFAULT_RESERVE = 0;

  @WireField(
      tag = 1,
      adapter = "com.squareup.wire.ProtoAdapter#INT32"
  )
  public final Integer reserve;

  public PBUsrFriendListReq(Integer reserve) {
    this(reserve, ByteString.EMPTY);
  }

  public PBUsrFriendListReq(Integer reserve, ByteString unknownFields) {
    super(ADAPTER, unknownFields);
    this.reserve = reserve;
  }

  @Override
  public Builder newBuilder() {
    Builder builder = new Builder();
    builder.reserve = reserve;
    builder.addUnknownFields(unknownFields());
    return builder;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof PBUsrFriendListReq)) return false;
    PBUsrFriendListReq o = (PBUsrFriendListReq) other;
    return Internal.equals(unknownFields(), o.unknownFields())
        && Internal.equals(reserve, o.reserve);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode;
    if (result == 0) {
      result = unknownFields().hashCode();
      result = result * 37 + (reserve != null ? reserve.hashCode() : 0);
      super.hashCode = result;
    }
    return result;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (reserve != null) builder.append(", reserve=").append(reserve);
    return builder.replace(0, 2, "PBUsrFriendListReq{").append('}').toString();
  }

  public static final class Builder extends Message.Builder<PBUsrFriendListReq, Builder> {
    public Integer reserve;

    public Builder() {
    }

    public Builder reserve(Integer reserve) {
      this.reserve = reserve;
      return this;
    }

    @Override
    public PBUsrFriendListReq build() {
      return new PBUsrFriendListReq(reserve, buildUnknownFields());
    }
  }

  private static final class ProtoAdapter_PBUsrFriendListReq extends ProtoAdapter<PBUsrFriendListReq> {
    ProtoAdapter_PBUsrFriendListReq() {
      super(FieldEncoding.LENGTH_DELIMITED, PBUsrFriendListReq.class);
    }

    @Override
    public int encodedSize(PBUsrFriendListReq value) {
      return (value.reserve != null ? ProtoAdapter.INT32.encodedSizeWithTag(1, value.reserve) : 0)
          + value.unknownFields().size();
    }

    @Override
    public void encode(ProtoWriter writer, PBUsrFriendListReq value) throws IOException {
      if (value.reserve != null) ProtoAdapter.INT32.encodeWithTag(writer, 1, value.reserve);
      writer.writeBytes(value.unknownFields());
    }

    @Override
    public PBUsrFriendListReq decode(ProtoReader reader) throws IOException {
      Builder builder = new Builder();
      long token = reader.beginMessage();
      for (int tag; (tag = reader.nextTag()) != -1;) {
        switch (tag) {
          case 1: builder.reserve(ProtoAdapter.INT32.decode(reader)); break;
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
    public PBUsrFriendListReq redact(PBUsrFriendListReq value) {
      Builder builder = value.newBuilder();
      builder.clearUnknownFields();
      return builder.build();
    }
  }
}