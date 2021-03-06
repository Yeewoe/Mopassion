// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: merge.proto at 161:1
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
 * ?????��????????
 */
public final class PBGroupGetReq extends Message<PBGroupGetReq, PBGroupGetReq.Builder> {
  public static final ProtoAdapter<PBGroupGetReq> ADAPTER = new ProtoAdapter_PBGroupGetReq();

  private static final long serialVersionUID = 0L;

  public static final Long DEFAULT_GID = 0L;

  @WireField(
      tag = 1,
      adapter = "org.yeewoe.mopanet.protos.PBGroupFilter#ADAPTER"
  )
  public final PBGroupFilter filter;

  @WireField(
      tag = 2,
      adapter = "com.squareup.wire.ProtoAdapter#UINT64",
      label = WireField.Label.REQUIRED
  )
  public final Long gid;

  public PBGroupGetReq(PBGroupFilter filter, Long gid) {
    this(filter, gid, ByteString.EMPTY);
  }

  public PBGroupGetReq(PBGroupFilter filter, Long gid, ByteString unknownFields) {
    super(ADAPTER, unknownFields);
    this.filter = filter;
    this.gid = gid;
  }

  @Override
  public Builder newBuilder() {
    Builder builder = new Builder();
    builder.filter = filter;
    builder.gid = gid;
    builder.addUnknownFields(unknownFields());
    return builder;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof PBGroupGetReq)) return false;
    PBGroupGetReq o = (PBGroupGetReq) other;
    return Internal.equals(unknownFields(), o.unknownFields())
        && Internal.equals(filter, o.filter)
        && Internal.equals(gid, o.gid);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode;
    if (result == 0) {
      result = unknownFields().hashCode();
      result = result * 37 + (filter != null ? filter.hashCode() : 0);
      result = result * 37 + (gid != null ? gid.hashCode() : 0);
      super.hashCode = result;
    }
    return result;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (filter != null) builder.append(", filter=").append(filter);
    if (gid != null) builder.append(", gid=").append(gid);
    return builder.replace(0, 2, "PBGroupGetReq{").append('}').toString();
  }

  public static final class Builder extends Message.Builder<PBGroupGetReq, Builder> {
    public PBGroupFilter filter;

    public Long gid;

    public Builder() {
    }

    public Builder filter(PBGroupFilter filter) {
      this.filter = filter;
      return this;
    }

    public Builder gid(Long gid) {
      this.gid = gid;
      return this;
    }

    @Override
    public PBGroupGetReq build() {
      if (gid == null) {
        throw Internal.missingRequiredFields(gid, "gid");
      }
      return new PBGroupGetReq(filter, gid, buildUnknownFields());
    }
  }

  private static final class ProtoAdapter_PBGroupGetReq extends ProtoAdapter<PBGroupGetReq> {
    ProtoAdapter_PBGroupGetReq() {
      super(FieldEncoding.LENGTH_DELIMITED, PBGroupGetReq.class);
    }

    @Override
    public int encodedSize(PBGroupGetReq value) {
      return (value.filter != null ? PBGroupFilter.ADAPTER.encodedSizeWithTag(1, value.filter) : 0)
          + ProtoAdapter.UINT64.encodedSizeWithTag(2, value.gid)
          + value.unknownFields().size();
    }

    @Override
    public void encode(ProtoWriter writer, PBGroupGetReq value) throws IOException {
      if (value.filter != null) PBGroupFilter.ADAPTER.encodeWithTag(writer, 1, value.filter);
      ProtoAdapter.UINT64.encodeWithTag(writer, 2, value.gid);
      writer.writeBytes(value.unknownFields());
    }

    @Override
    public PBGroupGetReq decode(ProtoReader reader) throws IOException {
      Builder builder = new Builder();
      long token = reader.beginMessage();
      for (int tag; (tag = reader.nextTag()) != -1;) {
        switch (tag) {
          case 1: builder.filter(PBGroupFilter.ADAPTER.decode(reader)); break;
          case 2: builder.gid(ProtoAdapter.UINT64.decode(reader)); break;
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
    public PBGroupGetReq redact(PBGroupGetReq value) {
      Builder builder = value.newBuilder();
      if (builder.filter != null) builder.filter = PBGroupFilter.ADAPTER.redact(builder.filter);
      builder.clearUnknownFields();
      return builder.build();
    }
  }
}
