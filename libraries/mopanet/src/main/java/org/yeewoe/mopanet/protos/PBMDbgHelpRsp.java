// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: merge.proto at 284:1
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

public final class PBMDbgHelpRsp extends Message<PBMDbgHelpRsp, PBMDbgHelpRsp.Builder> {
  public static final ProtoAdapter<PBMDbgHelpRsp> ADAPTER = new ProtoAdapter_PBMDbgHelpRsp();

  private static final long serialVersionUID = 0L;

  @WireField(
      tag = 1,
      adapter = "org.yeewoe.mopanet.protos.PBMDbgHelp#ADAPTER",
      label = WireField.Label.REPEATED
  )
  public final List<PBMDbgHelp> commands;

  public PBMDbgHelpRsp(List<PBMDbgHelp> commands) {
    this(commands, ByteString.EMPTY);
  }

  public PBMDbgHelpRsp(List<PBMDbgHelp> commands, ByteString unknownFields) {
    super(ADAPTER, unknownFields);
    this.commands = Internal.immutableCopyOf("commands", commands);
  }

  @Override
  public Builder newBuilder() {
    Builder builder = new Builder();
    builder.commands = Internal.copyOf("commands", commands);
    builder.addUnknownFields(unknownFields());
    return builder;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof PBMDbgHelpRsp)) return false;
    PBMDbgHelpRsp o = (PBMDbgHelpRsp) other;
    return Internal.equals(unknownFields(), o.unknownFields())
        && Internal.equals(commands, o.commands);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode;
    if (result == 0) {
      result = unknownFields().hashCode();
      result = result * 37 + (commands != null ? commands.hashCode() : 1);
      super.hashCode = result;
    }
    return result;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (commands != null) builder.append(", commands=").append(commands);
    return builder.replace(0, 2, "PBMDbgHelpRsp{").append('}').toString();
  }

  public static final class Builder extends Message.Builder<PBMDbgHelpRsp, Builder> {
    public List<PBMDbgHelp> commands;

    public Builder() {
      commands = Internal.newMutableList();
    }

    public Builder commands(List<PBMDbgHelp> commands) {
      Internal.checkElementsNotNull(commands);
      this.commands = commands;
      return this;
    }

    @Override
    public PBMDbgHelpRsp build() {
      return new PBMDbgHelpRsp(commands, buildUnknownFields());
    }
  }

  private static final class ProtoAdapter_PBMDbgHelpRsp extends ProtoAdapter<PBMDbgHelpRsp> {
    ProtoAdapter_PBMDbgHelpRsp() {
      super(FieldEncoding.LENGTH_DELIMITED, PBMDbgHelpRsp.class);
    }

    @Override
    public int encodedSize(PBMDbgHelpRsp value) {
      return PBMDbgHelp.ADAPTER.asRepeated().encodedSizeWithTag(1, value.commands)
          + value.unknownFields().size();
    }

    @Override
    public void encode(ProtoWriter writer, PBMDbgHelpRsp value) throws IOException {
      if (value.commands != null) PBMDbgHelp.ADAPTER.asRepeated().encodeWithTag(writer, 1, value.commands);
      writer.writeBytes(value.unknownFields());
    }

    @Override
    public PBMDbgHelpRsp decode(ProtoReader reader) throws IOException {
      Builder builder = new Builder();
      long token = reader.beginMessage();
      for (int tag; (tag = reader.nextTag()) != -1;) {
        switch (tag) {
          case 1: builder.commands.add(PBMDbgHelp.ADAPTER.decode(reader)); break;
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
    public PBMDbgHelpRsp redact(PBMDbgHelpRsp value) {
      Builder builder = value.newBuilder();
      Internal.redactElements(builder.commands, PBMDbgHelp.ADAPTER);
      builder.clearUnknownFields();
      return builder.build();
    }
  }
}