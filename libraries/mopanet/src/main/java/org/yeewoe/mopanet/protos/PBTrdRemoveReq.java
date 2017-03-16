// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: merge.proto at 376:1
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
 * ɾ����̬
 */
public final class PBTrdRemoveReq extends Message<PBTrdRemoveReq, PBTrdRemoveReq.Builder> {
  public static final ProtoAdapter<PBTrdRemoveReq> ADAPTER = new ProtoAdapter_PBTrdRemoveReq();

  private static final long serialVersionUID = 0L;

  public static final Long DEFAULT_ID = 0L;

  /**
   * 	Ҫɾ���Ķ�̬id
   */
  @WireField(
      tag = 1,
      adapter = "com.squareup.wire.ProtoAdapter#UINT64"
  )
  public final Long id;

  public PBTrdRemoveReq(Long id) {
    this(id, ByteString.EMPTY);
  }

  public PBTrdRemoveReq(Long id, ByteString unknownFields) {
    super(ADAPTER, unknownFields);
    this.id = id;
  }

  @Override
  public Builder newBuilder() {
    Builder builder = new Builder();
    builder.id = id;
    builder.addUnknownFields(unknownFields());
    return builder;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof PBTrdRemoveReq)) return false;
    PBTrdRemoveReq o = (PBTrdRemoveReq) other;
    return Internal.equals(unknownFields(), o.unknownFields())
        && Internal.equals(id, o.id);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode;
    if (result == 0) {
      result = unknownFields().hashCode();
      result = result * 37 + (id != null ? id.hashCode() : 0);
      super.hashCode = result;
    }
    return result;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (id != null) builder.append(", id=").append(id);
    return builder.replace(0, 2, "PBTrdRemoveReq{").append('}').toString();
  }

  public static final class Builder extends Message.Builder<PBTrdRemoveReq, Builder> {
    public Long id;

    public Builder() {
    }

    /**
     * 	Ҫɾ���Ķ�̬id
     */
    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    @Override
    public PBTrdRemoveReq build() {
      return new PBTrdRemoveReq(id, buildUnknownFields());
    }
  }

  private static final class ProtoAdapter_PBTrdRemoveReq extends ProtoAdapter<PBTrdRemoveReq> {
    ProtoAdapter_PBTrdRemoveReq() {
      super(FieldEncoding.LENGTH_DELIMITED, PBTrdRemoveReq.class);
    }

    @Override
    public int encodedSize(PBTrdRemoveReq value) {
      return (value.id != null ? ProtoAdapter.UINT64.encodedSizeWithTag(1, value.id) : 0)
          + value.unknownFields().size();
    }

    @Override
    public void encode(ProtoWriter writer, PBTrdRemoveReq value) throws IOException {
      if (value.id != null) ProtoAdapter.UINT64.encodeWithTag(writer, 1, value.id);
      writer.writeBytes(value.unknownFields());
    }

    @Override
    public PBTrdRemoveReq decode(ProtoReader reader) throws IOException {
      Builder builder = new Builder();
      long token = reader.beginMessage();
      for (int tag; (tag = reader.nextTag()) != -1;) {
        switch (tag) {
          case 1: builder.id(ProtoAdapter.UINT64.decode(reader)); break;
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
    public PBTrdRemoveReq redact(PBTrdRemoveReq value) {
      Builder builder = value.newBuilder();
      builder.clearUnknownFields();
      return builder.build();
    }
  }
}