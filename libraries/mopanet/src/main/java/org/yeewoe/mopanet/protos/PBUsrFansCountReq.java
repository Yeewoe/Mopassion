// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: merge.proto at 703:1
package org.yeewoe.mopanet.protos;

import com.squareup.wire.FieldEncoding;
import com.squareup.wire.Message;
import com.squareup.wire.ProtoAdapter;
import com.squareup.wire.ProtoReader;
import com.squareup.wire.ProtoWriter;
import com.squareup.wire.WireField;
import com.squareup.wire.internal.Internal;
import java.io.IOException;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import okio.ByteString;

/**
 * ��ȡ��˿����
 */
public final class PBUsrFansCountReq extends Message<PBUsrFansCountReq, PBUsrFansCountReq.Builder> {
  public static final ProtoAdapter<PBUsrFansCountReq> ADAPTER = new ProtoAdapter_PBUsrFansCountReq();

  private static final long serialVersionUID = 0L;

  public static final Long DEFAULT_UID = 0L;

  /**
   * ��ѯ���û�id
   */
  @WireField(
      tag = 2,
      adapter = "com.squareup.wire.ProtoAdapter#UINT64"
  )
  public final Long uid;

  public PBUsrFansCountReq(Long uid) {
    this(uid, ByteString.EMPTY);
  }

  public PBUsrFansCountReq(Long uid, ByteString unknownFields) {
    super(ADAPTER, unknownFields);
    this.uid = uid;
  }

  @Override
  public Builder newBuilder() {
    Builder builder = new Builder();
    builder.uid = uid;
    builder.addUnknownFields(unknownFields());
    return builder;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof PBUsrFansCountReq)) return false;
    PBUsrFansCountReq o = (PBUsrFansCountReq) other;
    return Internal.equals(unknownFields(), o.unknownFields())
        && Internal.equals(uid, o.uid);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode;
    if (result == 0) {
      result = unknownFields().hashCode();
      result = result * 37 + (uid != null ? uid.hashCode() : 0);
      super.hashCode = result;
    }
    return result;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (uid != null) builder.append(", uid=").append(uid);
    return builder.replace(0, 2, "PBUsrFansCountReq{").append('}').toString();
  }

  public static final class Builder extends Message.Builder<PBUsrFansCountReq, Builder> {
    public Long uid;

    public Builder() {
    }

    /**
     * ��ѯ���û�id
     */
    public Builder uid(Long uid) {
      this.uid = uid;
      return this;
    }

    @Override
    public PBUsrFansCountReq build() {
      return new PBUsrFansCountReq(uid, buildUnknownFields());
    }
  }

  private static final class ProtoAdapter_PBUsrFansCountReq extends ProtoAdapter<PBUsrFansCountReq> {
    ProtoAdapter_PBUsrFansCountReq() {
      super(FieldEncoding.LENGTH_DELIMITED, PBUsrFansCountReq.class);
    }

    @Override
    public int encodedSize(PBUsrFansCountReq value) {
      return (value.uid != null ? ProtoAdapter.UINT64.encodedSizeWithTag(2, value.uid) : 0)
          + value.unknownFields().size();
    }

    @Override
    public void encode(ProtoWriter writer, PBUsrFansCountReq value) throws IOException {
      if (value.uid != null) ProtoAdapter.UINT64.encodeWithTag(writer, 2, value.uid);
      writer.writeBytes(value.unknownFields());
    }

    @Override
    public PBUsrFansCountReq decode(ProtoReader reader) throws IOException {
      Builder builder = new Builder();
      long token = reader.beginMessage();
      for (int tag; (tag = reader.nextTag()) != -1;) {
        switch (tag) {
          case 2: builder.uid(ProtoAdapter.UINT64.decode(reader)); break;
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
    public PBUsrFansCountReq redact(PBUsrFansCountReq value) {
      Builder builder = value.newBuilder();
      builder.clearUnknownFields();
      return builder.build();
    }
  }
}
