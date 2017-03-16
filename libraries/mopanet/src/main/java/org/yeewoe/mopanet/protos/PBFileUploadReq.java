// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: merge.proto at 58:1
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
import java.util.List;
import okio.ByteString;

public final class PBFileUploadReq extends Message<PBFileUploadReq, PBFileUploadReq.Builder> {
  public static final ProtoAdapter<PBFileUploadReq> ADAPTER = new ProtoAdapter_PBFileUploadReq();

  private static final long serialVersionUID = 0L;

  /**
   * ??????????��?
   */
  @WireField(
      tag = 1,
      adapter = "org.yeewoe.mopanet.protos.PBFileUploadInfo#ADAPTER",
      label = WireField.Label.REPEATED
  )
  public final List<PBFileUploadInfo> infos;

  public PBFileUploadReq(List<PBFileUploadInfo> infos) {
    this(infos, ByteString.EMPTY);
  }

  public PBFileUploadReq(List<PBFileUploadInfo> infos, ByteString unknownFields) {
    super(ADAPTER, unknownFields);
    this.infos = Internal.immutableCopyOf("infos", infos);
  }

  @Override
  public Builder newBuilder() {
    Builder builder = new Builder();
    builder.infos = Internal.copyOf("infos", infos);
    builder.addUnknownFields(unknownFields());
    return builder;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof PBFileUploadReq)) return false;
    PBFileUploadReq o = (PBFileUploadReq) other;
    return Internal.equals(unknownFields(), o.unknownFields())
        && Internal.equals(infos, o.infos);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode;
    if (result == 0) {
      result = unknownFields().hashCode();
      result = result * 37 + (infos != null ? infos.hashCode() : 1);
      super.hashCode = result;
    }
    return result;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (infos != null) builder.append(", infos=").append(infos);
    return builder.replace(0, 2, "PBFileUploadReq{").append('}').toString();
  }

  public static final class Builder extends Message.Builder<PBFileUploadReq, Builder> {
    public List<PBFileUploadInfo> infos;

    public Builder() {
      infos = Internal.newMutableList();
    }

    /**
     * ??????????��?
     */
    public Builder infos(List<PBFileUploadInfo> infos) {
      Internal.checkElementsNotNull(infos);
      this.infos = infos;
      return this;
    }

    @Override
    public PBFileUploadReq build() {
      return new PBFileUploadReq(infos, buildUnknownFields());
    }
  }

  private static final class ProtoAdapter_PBFileUploadReq extends ProtoAdapter<PBFileUploadReq> {
    ProtoAdapter_PBFileUploadReq() {
      super(FieldEncoding.LENGTH_DELIMITED, PBFileUploadReq.class);
    }

    @Override
    public int encodedSize(PBFileUploadReq value) {
      return PBFileUploadInfo.ADAPTER.asRepeated().encodedSizeWithTag(1, value.infos)
          + value.unknownFields().size();
    }

    @Override
    public void encode(ProtoWriter writer, PBFileUploadReq value) throws IOException {
      if (value.infos != null) PBFileUploadInfo.ADAPTER.asRepeated().encodeWithTag(writer, 1, value.infos);
      writer.writeBytes(value.unknownFields());
    }

    @Override
    public PBFileUploadReq decode(ProtoReader reader) throws IOException {
      Builder builder = new Builder();
      long token = reader.beginMessage();
      for (int tag; (tag = reader.nextTag()) != -1;) {
        switch (tag) {
          case 1: builder.infos.add(PBFileUploadInfo.ADAPTER.decode(reader)); break;
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
    public PBFileUploadReq redact(PBFileUploadReq value) {
      Builder builder = value.newBuilder();
      Internal.redactElements(builder.infos, PBFileUploadInfo.ADAPTER);
      builder.clearUnknownFields();
      return builder.build();
    }
  }
}
