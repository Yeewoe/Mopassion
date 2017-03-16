// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: merge.proto at 627:1
package org.yeewoe.mopanet.protos;

import com.squareup.wire.FieldEncoding;
import com.squareup.wire.Message;
import com.squareup.wire.ProtoAdapter;
import com.squareup.wire.ProtoReader;
import com.squareup.wire.ProtoWriter;
import com.squareup.wire.WireField;
import com.squareup.wire.internal.Internal;
import java.io.IOException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import okio.ByteString;

/**
 * �޸�����
 */
public final class PBUsrModifyPasswdReq extends Message<PBUsrModifyPasswdReq, PBUsrModifyPasswdReq.Builder> {
  public static final ProtoAdapter<PBUsrModifyPasswdReq> ADAPTER = new ProtoAdapter_PBUsrModifyPasswdReq();

  private static final long serialVersionUID = 0L;

  public static final ByteString DEFAULT_OLDPASSWD = ByteString.EMPTY;

  public static final ByteString DEFAULT_NEWPASSWD = ByteString.EMPTY;

  @WireField(
      tag = 1,
      adapter = "com.squareup.wire.ProtoAdapter#BYTES",
      label = WireField.Label.REQUIRED
  )
  public final ByteString oldpasswd;

  @WireField(
      tag = 2,
      adapter = "com.squareup.wire.ProtoAdapter#BYTES",
      label = WireField.Label.REQUIRED
  )
  public final ByteString newpasswd;

  public PBUsrModifyPasswdReq(ByteString oldpasswd, ByteString newpasswd) {
    this(oldpasswd, newpasswd, ByteString.EMPTY);
  }

  public PBUsrModifyPasswdReq(ByteString oldpasswd, ByteString newpasswd, ByteString unknownFields) {
    super(ADAPTER, unknownFields);
    this.oldpasswd = oldpasswd;
    this.newpasswd = newpasswd;
  }

  @Override
  public Builder newBuilder() {
    Builder builder = new Builder();
    builder.oldpasswd = oldpasswd;
    builder.newpasswd = newpasswd;
    builder.addUnknownFields(unknownFields());
    return builder;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof PBUsrModifyPasswdReq)) return false;
    PBUsrModifyPasswdReq o = (PBUsrModifyPasswdReq) other;
    return Internal.equals(unknownFields(), o.unknownFields())
        && Internal.equals(oldpasswd, o.oldpasswd)
        && Internal.equals(newpasswd, o.newpasswd);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode;
    if (result == 0) {
      result = unknownFields().hashCode();
      result = result * 37 + (oldpasswd != null ? oldpasswd.hashCode() : 0);
      result = result * 37 + (newpasswd != null ? newpasswd.hashCode() : 0);
      super.hashCode = result;
    }
    return result;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (oldpasswd != null) builder.append(", oldpasswd=").append(oldpasswd);
    if (newpasswd != null) builder.append(", newpasswd=").append(newpasswd);
    return builder.replace(0, 2, "PBUsrModifyPasswdReq{").append('}').toString();
  }

  public static final class Builder extends Message.Builder<PBUsrModifyPasswdReq, Builder> {
    public ByteString oldpasswd;

    public ByteString newpasswd;

    public Builder() {
    }

    public Builder oldpasswd(ByteString oldpasswd) {
      this.oldpasswd = oldpasswd;
      return this;
    }

    public Builder newpasswd(ByteString newpasswd) {
      this.newpasswd = newpasswd;
      return this;
    }

    @Override
    public PBUsrModifyPasswdReq build() {
      if (oldpasswd == null
          || newpasswd == null) {
        throw Internal.missingRequiredFields(oldpasswd, "oldpasswd",
            newpasswd, "newpasswd");
      }
      return new PBUsrModifyPasswdReq(oldpasswd, newpasswd, buildUnknownFields());
    }
  }

  private static final class ProtoAdapter_PBUsrModifyPasswdReq extends ProtoAdapter<PBUsrModifyPasswdReq> {
    ProtoAdapter_PBUsrModifyPasswdReq() {
      super(FieldEncoding.LENGTH_DELIMITED, PBUsrModifyPasswdReq.class);
    }

    @Override
    public int encodedSize(PBUsrModifyPasswdReq value) {
      return ProtoAdapter.BYTES.encodedSizeWithTag(1, value.oldpasswd)
          + ProtoAdapter.BYTES.encodedSizeWithTag(2, value.newpasswd)
          + value.unknownFields().size();
    }

    @Override
    public void encode(ProtoWriter writer, PBUsrModifyPasswdReq value) throws IOException {
      ProtoAdapter.BYTES.encodeWithTag(writer, 1, value.oldpasswd);
      ProtoAdapter.BYTES.encodeWithTag(writer, 2, value.newpasswd);
      writer.writeBytes(value.unknownFields());
    }

    @Override
    public PBUsrModifyPasswdReq decode(ProtoReader reader) throws IOException {
      Builder builder = new Builder();
      long token = reader.beginMessage();
      for (int tag; (tag = reader.nextTag()) != -1;) {
        switch (tag) {
          case 1: builder.oldpasswd(ProtoAdapter.BYTES.decode(reader)); break;
          case 2: builder.newpasswd(ProtoAdapter.BYTES.decode(reader)); break;
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
    public PBUsrModifyPasswdReq redact(PBUsrModifyPasswdReq value) {
      Builder builder = value.newBuilder();
      builder.clearUnknownFields();
      return builder.build();
    }
  }
}