// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: merge.proto at 150:1
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
import java.util.List;
import okio.ByteString;

/**
 * ?????????
 */
public final class PBGroupDelMemberReq extends Message<PBGroupDelMemberReq, PBGroupDelMemberReq.Builder> {
  public static final ProtoAdapter<PBGroupDelMemberReq> ADAPTER = new ProtoAdapter_PBGroupDelMemberReq();

  private static final long serialVersionUID = 0L;

  public static final Long DEFAULT_GID = 0L;

  @WireField(
      tag = 1,
      adapter = "com.squareup.wire.ProtoAdapter#UINT64",
      label = WireField.Label.REQUIRED
  )
  public final Long gid;

  @WireField(
      tag = 2,
      adapter = "com.squareup.wire.ProtoAdapter#UINT64",
      label = WireField.Label.REPEATED
  )
  public final List<Long> members;

  public PBGroupDelMemberReq(Long gid, List<Long> members) {
    this(gid, members, ByteString.EMPTY);
  }

  public PBGroupDelMemberReq(Long gid, List<Long> members, ByteString unknownFields) {
    super(ADAPTER, unknownFields);
    this.gid = gid;
    this.members = Internal.immutableCopyOf("members", members);
  }

  @Override
  public Builder newBuilder() {
    Builder builder = new Builder();
    builder.gid = gid;
    builder.members = Internal.copyOf("members", members);
    builder.addUnknownFields(unknownFields());
    return builder;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof PBGroupDelMemberReq)) return false;
    PBGroupDelMemberReq o = (PBGroupDelMemberReq) other;
    return Internal.equals(unknownFields(), o.unknownFields())
        && Internal.equals(gid, o.gid)
        && Internal.equals(members, o.members);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode;
    if (result == 0) {
      result = unknownFields().hashCode();
      result = result * 37 + (gid != null ? gid.hashCode() : 0);
      result = result * 37 + (members != null ? members.hashCode() : 1);
      super.hashCode = result;
    }
    return result;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (gid != null) builder.append(", gid=").append(gid);
    if (members != null) builder.append(", members=").append(members);
    return builder.replace(0, 2, "PBGroupDelMemberReq{").append('}').toString();
  }

  public static final class Builder extends Message.Builder<PBGroupDelMemberReq, Builder> {
    public Long gid;

    public List<Long> members;

    public Builder() {
      members = Internal.newMutableList();
    }

    public Builder gid(Long gid) {
      this.gid = gid;
      return this;
    }

    public Builder members(List<Long> members) {
      Internal.checkElementsNotNull(members);
      this.members = members;
      return this;
    }

    @Override
    public PBGroupDelMemberReq build() {
      if (gid == null) {
        throw Internal.missingRequiredFields(gid, "gid");
      }
      return new PBGroupDelMemberReq(gid, members, buildUnknownFields());
    }
  }

  private static final class ProtoAdapter_PBGroupDelMemberReq extends ProtoAdapter<PBGroupDelMemberReq> {
    ProtoAdapter_PBGroupDelMemberReq() {
      super(FieldEncoding.LENGTH_DELIMITED, PBGroupDelMemberReq.class);
    }

    @Override
    public int encodedSize(PBGroupDelMemberReq value) {
      return ProtoAdapter.UINT64.encodedSizeWithTag(1, value.gid)
          + ProtoAdapter.UINT64.asRepeated().encodedSizeWithTag(2, value.members)
          + value.unknownFields().size();
    }

    @Override
    public void encode(ProtoWriter writer, PBGroupDelMemberReq value) throws IOException {
      ProtoAdapter.UINT64.encodeWithTag(writer, 1, value.gid);
      if (value.members != null) ProtoAdapter.UINT64.asRepeated().encodeWithTag(writer, 2, value.members);
      writer.writeBytes(value.unknownFields());
    }

    @Override
    public PBGroupDelMemberReq decode(ProtoReader reader) throws IOException {
      Builder builder = new Builder();
      long token = reader.beginMessage();
      for (int tag; (tag = reader.nextTag()) != -1;) {
        switch (tag) {
          case 1: builder.gid(ProtoAdapter.UINT64.decode(reader)); break;
          case 2: builder.members.add(ProtoAdapter.UINT64.decode(reader)); break;
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
    public PBGroupDelMemberReq redact(PBGroupDelMemberReq value) {
      Builder builder = value.newBuilder();
      builder.clearUnknownFields();
      return builder.build();
    }
  }
}
