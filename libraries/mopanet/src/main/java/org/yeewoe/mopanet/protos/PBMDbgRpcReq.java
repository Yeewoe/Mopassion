// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: merge.proto at 264:1
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

public final class PBMDbgRpcReq extends Message<PBMDbgRpcReq, PBMDbgRpcReq.Builder> {
  public static final ProtoAdapter<PBMDbgRpcReq> ADAPTER = new ProtoAdapter_PBMDbgRpcReq();

  private static final long serialVersionUID = 0L;

  public static final String DEFAULT_CMDNAME = "";

  public static final ByteString DEFAULT_CMDREQ = ByteString.EMPTY;

  public static final String DEFAULT_UNAME = "";

  public static final Integer DEFAULT_UID = 0;

  @WireField(
      tag = 1,
      adapter = "com.squareup.wire.ProtoAdapter#STRING",
      label = WireField.Label.REQUIRED
  )
  public final String cmdname;

  @WireField(
      tag = 2,
      adapter = "com.squareup.wire.ProtoAdapter#BYTES",
      label = WireField.Label.REQUIRED
  )
  public final ByteString cmdreq;

  @WireField(
      tag = 3,
      adapter = "com.squareup.wire.ProtoAdapter#STRING"
  )
  public final String uname;

  @WireField(
      tag = 4,
      adapter = "com.squareup.wire.ProtoAdapter#INT32"
  )
  public final Integer uid;

  public PBMDbgRpcReq(String cmdname, ByteString cmdreq, String uname, Integer uid) {
    this(cmdname, cmdreq, uname, uid, ByteString.EMPTY);
  }

  public PBMDbgRpcReq(String cmdname, ByteString cmdreq, String uname, Integer uid, ByteString unknownFields) {
    super(ADAPTER, unknownFields);
    this.cmdname = cmdname;
    this.cmdreq = cmdreq;
    this.uname = uname;
    this.uid = uid;
  }

  @Override
  public Builder newBuilder() {
    Builder builder = new Builder();
    builder.cmdname = cmdname;
    builder.cmdreq = cmdreq;
    builder.uname = uname;
    builder.uid = uid;
    builder.addUnknownFields(unknownFields());
    return builder;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof PBMDbgRpcReq)) return false;
    PBMDbgRpcReq o = (PBMDbgRpcReq) other;
    return Internal.equals(unknownFields(), o.unknownFields())
        && Internal.equals(cmdname, o.cmdname)
        && Internal.equals(cmdreq, o.cmdreq)
        && Internal.equals(uname, o.uname)
        && Internal.equals(uid, o.uid);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode;
    if (result == 0) {
      result = unknownFields().hashCode();
      result = result * 37 + (cmdname != null ? cmdname.hashCode() : 0);
      result = result * 37 + (cmdreq != null ? cmdreq.hashCode() : 0);
      result = result * 37 + (uname != null ? uname.hashCode() : 0);
      result = result * 37 + (uid != null ? uid.hashCode() : 0);
      super.hashCode = result;
    }
    return result;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (cmdname != null) builder.append(", cmdname=").append(cmdname);
    if (cmdreq != null) builder.append(", cmdreq=").append(cmdreq);
    if (uname != null) builder.append(", uname=").append(uname);
    if (uid != null) builder.append(", uid=").append(uid);
    return builder.replace(0, 2, "PBMDbgRpcReq{").append('}').toString();
  }

  public static final class Builder extends Message.Builder<PBMDbgRpcReq, Builder> {
    public String cmdname;

    public ByteString cmdreq;

    public String uname;

    public Integer uid;

    public Builder() {
    }

    public Builder cmdname(String cmdname) {
      this.cmdname = cmdname;
      return this;
    }

    public Builder cmdreq(ByteString cmdreq) {
      this.cmdreq = cmdreq;
      return this;
    }

    public Builder uname(String uname) {
      this.uname = uname;
      return this;
    }

    public Builder uid(Integer uid) {
      this.uid = uid;
      return this;
    }

    @Override
    public PBMDbgRpcReq build() {
      if (cmdname == null
          || cmdreq == null) {
        throw Internal.missingRequiredFields(cmdname, "cmdname",
            cmdreq, "cmdreq");
      }
      return new PBMDbgRpcReq(cmdname, cmdreq, uname, uid, buildUnknownFields());
    }
  }

  private static final class ProtoAdapter_PBMDbgRpcReq extends ProtoAdapter<PBMDbgRpcReq> {
    ProtoAdapter_PBMDbgRpcReq() {
      super(FieldEncoding.LENGTH_DELIMITED, PBMDbgRpcReq.class);
    }

    @Override
    public int encodedSize(PBMDbgRpcReq value) {
      return ProtoAdapter.STRING.encodedSizeWithTag(1, value.cmdname)
          + ProtoAdapter.BYTES.encodedSizeWithTag(2, value.cmdreq)
          + (value.uname != null ? ProtoAdapter.STRING.encodedSizeWithTag(3, value.uname) : 0)
          + (value.uid != null ? ProtoAdapter.INT32.encodedSizeWithTag(4, value.uid) : 0)
          + value.unknownFields().size();
    }

    @Override
    public void encode(ProtoWriter writer, PBMDbgRpcReq value) throws IOException {
      ProtoAdapter.STRING.encodeWithTag(writer, 1, value.cmdname);
      ProtoAdapter.BYTES.encodeWithTag(writer, 2, value.cmdreq);
      if (value.uname != null) ProtoAdapter.STRING.encodeWithTag(writer, 3, value.uname);
      if (value.uid != null) ProtoAdapter.INT32.encodeWithTag(writer, 4, value.uid);
      writer.writeBytes(value.unknownFields());
    }

    @Override
    public PBMDbgRpcReq decode(ProtoReader reader) throws IOException {
      Builder builder = new Builder();
      long token = reader.beginMessage();
      for (int tag; (tag = reader.nextTag()) != -1;) {
        switch (tag) {
          case 1: builder.cmdname(ProtoAdapter.STRING.decode(reader)); break;
          case 2: builder.cmdreq(ProtoAdapter.BYTES.decode(reader)); break;
          case 3: builder.uname(ProtoAdapter.STRING.decode(reader)); break;
          case 4: builder.uid(ProtoAdapter.INT32.decode(reader)); break;
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
    public PBMDbgRpcReq redact(PBMDbgRpcReq value) {
      Builder builder = value.newBuilder();
      builder.clearUnknownFields();
      return builder.build();
    }
  }
}