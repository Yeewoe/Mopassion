// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: merge.proto at 13:1
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

/**
 * ?????
 */
public final class PBMedia extends Message<PBMedia, PBMedia.Builder> {
  public static final ProtoAdapter<PBMedia> ADAPTER = new ProtoAdapter_PBMedia();

  private static final long serialVersionUID = 0L;

  public static final Long DEFAULT_ID = 0L;

  public static final String DEFAULT_NAME = "";

  public static final Integer DEFAULT_TYPE = 0;

  public static final Long DEFAULT_SIZE = 0L;

  public static final ByteString DEFAULT_CONTENT = ByteString.EMPTY;

  public static final ByteString DEFAULT_EXT = ByteString.EMPTY;

  /**
   * 	???id
   */
  @WireField(
      tag = 1,
      adapter = "com.squareup.wire.ProtoAdapter#UINT64"
  )
  public final Long id;

  /**
   * 	???????
   */
  @WireField(
      tag = 2,
      adapter = "com.squareup.wire.ProtoAdapter#STRING"
  )
  public final String name;

  /**
   * 	???????(???PBMediaType)
   */
  @WireField(
      tag = 3,
      adapter = "com.squareup.wire.ProtoAdapter#INT32"
  )
  public final Integer type;

  /**
   * 	????��
   */
  @WireField(
      tag = 4,
      adapter = "com.squareup.wire.ProtoAdapter#INT64"
  )
  public final Long size;

  /**
   * 	???????(??????URL,??????...)
   */
  @WireField(
      tag = 5,
      adapter = "com.squareup.wire.ProtoAdapter#BYTES"
  )
  public final ByteString content;

  /**
   * 	????????????��?????????????????
   */
  @WireField(
      tag = 6,
      adapter = "com.squareup.wire.ProtoAdapter#BYTES"
  )
  public final ByteString ext;

  public PBMedia(Long id, String name, Integer type, Long size, ByteString content, ByteString ext) {
    this(id, name, type, size, content, ext, ByteString.EMPTY);
  }

  public PBMedia(Long id, String name, Integer type, Long size, ByteString content, ByteString ext, ByteString unknownFields) {
    super(ADAPTER, unknownFields);
    this.id = id;
    this.name = name;
    this.type = type;
    this.size = size;
    this.content = content;
    this.ext = ext;
  }

  @Override
  public Builder newBuilder() {
    Builder builder = new Builder();
    builder.id = id;
    builder.name = name;
    builder.type = type;
    builder.size = size;
    builder.content = content;
    builder.ext = ext;
    builder.addUnknownFields(unknownFields());
    return builder;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof PBMedia)) return false;
    PBMedia o = (PBMedia) other;
    return Internal.equals(unknownFields(), o.unknownFields())
        && Internal.equals(id, o.id)
        && Internal.equals(name, o.name)
        && Internal.equals(type, o.type)
        && Internal.equals(size, o.size)
        && Internal.equals(content, o.content)
        && Internal.equals(ext, o.ext);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode;
    if (result == 0) {
      result = unknownFields().hashCode();
      result = result * 37 + (id != null ? id.hashCode() : 0);
      result = result * 37 + (name != null ? name.hashCode() : 0);
      result = result * 37 + (type != null ? type.hashCode() : 0);
      result = result * 37 + (size != null ? size.hashCode() : 0);
      result = result * 37 + (content != null ? content.hashCode() : 0);
      result = result * 37 + (ext != null ? ext.hashCode() : 0);
      super.hashCode = result;
    }
    return result;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (id != null) builder.append(", id=").append(id);
    if (name != null) builder.append(", name=").append(name);
    if (type != null) builder.append(", type=").append(type);
    if (size != null) builder.append(", size=").append(size);
    if (content != null) builder.append(", content=").append(content);
    if (ext != null) builder.append(", ext=").append(ext);
    return builder.replace(0, 2, "PBMedia{").append('}').toString();
  }

  public static final class Builder extends Message.Builder<PBMedia, Builder> {
    public Long id;

    public String name;

    public Integer type;

    public Long size;

    public ByteString content;

    public ByteString ext;

    public Builder() {
    }

    /**
     * 	???id
     */
    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    /**
     * 	???????
     */
    public Builder name(String name) {
      this.name = name;
      return this;
    }

    /**
     * 	???????(???PBMediaType)
     */
    public Builder type(Integer type) {
      this.type = type;
      return this;
    }

    /**
     * 	????��
     */
    public Builder size(Long size) {
      this.size = size;
      return this;
    }

    /**
     * 	???????(??????URL,??????...)
     */
    public Builder content(ByteString content) {
      this.content = content;
      return this;
    }

    /**
     * 	????????????��?????????????????
     */
    public Builder ext(ByteString ext) {
      this.ext = ext;
      return this;
    }

    @Override
    public PBMedia build() {
      return new PBMedia(id, name, type, size, content, ext, buildUnknownFields());
    }
  }

  private static final class ProtoAdapter_PBMedia extends ProtoAdapter<PBMedia> {
    ProtoAdapter_PBMedia() {
      super(FieldEncoding.LENGTH_DELIMITED, PBMedia.class);
    }

    @Override
    public int encodedSize(PBMedia value) {
      return (value.id != null ? ProtoAdapter.UINT64.encodedSizeWithTag(1, value.id) : 0)
          + (value.name != null ? ProtoAdapter.STRING.encodedSizeWithTag(2, value.name) : 0)
          + (value.type != null ? ProtoAdapter.INT32.encodedSizeWithTag(3, value.type) : 0)
          + (value.size != null ? ProtoAdapter.INT64.encodedSizeWithTag(4, value.size) : 0)
          + (value.content != null ? ProtoAdapter.BYTES.encodedSizeWithTag(5, value.content) : 0)
          + (value.ext != null ? ProtoAdapter.BYTES.encodedSizeWithTag(6, value.ext) : 0)
          + value.unknownFields().size();
    }

    @Override
    public void encode(ProtoWriter writer, PBMedia value) throws IOException {
      if (value.id != null) ProtoAdapter.UINT64.encodeWithTag(writer, 1, value.id);
      if (value.name != null) ProtoAdapter.STRING.encodeWithTag(writer, 2, value.name);
      if (value.type != null) ProtoAdapter.INT32.encodeWithTag(writer, 3, value.type);
      if (value.size != null) ProtoAdapter.INT64.encodeWithTag(writer, 4, value.size);
      if (value.content != null) ProtoAdapter.BYTES.encodeWithTag(writer, 5, value.content);
      if (value.ext != null) ProtoAdapter.BYTES.encodeWithTag(writer, 6, value.ext);
      writer.writeBytes(value.unknownFields());
    }

    @Override
    public PBMedia decode(ProtoReader reader) throws IOException {
      Builder builder = new Builder();
      long token = reader.beginMessage();
      for (int tag; (tag = reader.nextTag()) != -1;) {
        switch (tag) {
          case 1: builder.id(ProtoAdapter.UINT64.decode(reader)); break;
          case 2: builder.name(ProtoAdapter.STRING.decode(reader)); break;
          case 3: builder.type(ProtoAdapter.INT32.decode(reader)); break;
          case 4: builder.size(ProtoAdapter.INT64.decode(reader)); break;
          case 5: builder.content(ProtoAdapter.BYTES.decode(reader)); break;
          case 6: builder.ext(ProtoAdapter.BYTES.decode(reader)); break;
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
    public PBMedia redact(PBMedia value) {
      Builder builder = value.newBuilder();
      builder.clearUnknownFields();
      return builder.build();
    }
  }
}