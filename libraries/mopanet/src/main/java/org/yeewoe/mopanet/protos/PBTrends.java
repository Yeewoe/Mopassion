// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: merge.proto at 351:1
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
import java.util.List;
import okio.ByteString;

public final class PBTrends extends Message<PBTrends, PBTrends.Builder> {
  public static final ProtoAdapter<PBTrends> ADAPTER = new ProtoAdapter_PBTrends();

  private static final long serialVersionUID = 0L;

  public static final Long DEFAULT_ID = 0L;

  public static final Long DEFAULT_UID = 0L;

  public static final Integer DEFAULT_VIEW = 0;

  public static final Integer DEFAULT_PURPOSE = 0;

  public static final Long DEFAULT_PUBTIME = 0L;

  public static final Integer DEFAULT_LIKECNT = 0;

  public static final Integer DEFAULT_LIKED = 0;

  /**
   * 	��̬id(�ɷ���˷���)
   */
  @WireField(
      tag = 1,
      adapter = "com.squareup.wire.ProtoAdapter#UINT64",
      label = WireField.Label.REQUIRED
  )
  public final Long id;

  /**
   * 	��̬�ķ�����
   */
  @WireField(
      tag = 2,
      adapter = "com.squareup.wire.ProtoAdapter#UINT64"
  )
  public final Long uid;

  /**
   * 	��̬����
   */
  @WireField(
      tag = 3,
      adapter = "org.yeewoe.mopanet.protos.PBMedia#ADAPTER",
      label = WireField.Label.REPEATED
  )
  public final List<PBMedia> contents;

  /**
   * 	��̬�Ŀɼ���(PBTrendsView)
   */
  @WireField(
      tag = 4,
      adapter = "com.squareup.wire.ProtoAdapter#INT32"
  )
  public final Integer view;

  /**
   * ��̬��Ŀ��,PBTrendsPurpose
   */
  @WireField(
      tag = 5,
      adapter = "com.squareup.wire.ProtoAdapter#INT32"
  )
  public final Integer purpose;

  /**
   * 	����ʱ��
   */
  @WireField(
      tag = 6,
      adapter = "com.squareup.wire.ProtoAdapter#UINT64"
  )
  public final Long pubtime;

  /**
   * 	�����ص�
   */
  @WireField(
      tag = 7,
      adapter = "org.yeewoe.mopanet.protos.PBMapPostion#ADAPTER"
  )
  public final PBMapPostion pubpostion;

  /**
   * ���޵�����
   */
  @WireField(
      tag = 8,
      adapter = "com.squareup.wire.ProtoAdapter#UINT32"
  )
  public final Integer likecnt;

  /**
   * ��ǰ�û��Ƿ����
   */
  @WireField(
      tag = 9,
      adapter = "com.squareup.wire.ProtoAdapter#UINT32"
  )
  public final Integer liked;

  public PBTrends(Long id, Long uid, List<PBMedia> contents, Integer view, Integer purpose, Long pubtime, PBMapPostion pubpostion, Integer likecnt, Integer liked) {
    this(id, uid, contents, view, purpose, pubtime, pubpostion, likecnt, liked, ByteString.EMPTY);
  }

  public PBTrends(Long id, Long uid, List<PBMedia> contents, Integer view, Integer purpose, Long pubtime, PBMapPostion pubpostion, Integer likecnt, Integer liked, ByteString unknownFields) {
    super(ADAPTER, unknownFields);
    this.id = id;
    this.uid = uid;
    this.contents = Internal.immutableCopyOf("contents", contents);
    this.view = view;
    this.purpose = purpose;
    this.pubtime = pubtime;
    this.pubpostion = pubpostion;
    this.likecnt = likecnt;
    this.liked = liked;
  }

  @Override
  public Builder newBuilder() {
    Builder builder = new Builder();
    builder.id = id;
    builder.uid = uid;
    builder.contents = Internal.copyOf("contents", contents);
    builder.view = view;
    builder.purpose = purpose;
    builder.pubtime = pubtime;
    builder.pubpostion = pubpostion;
    builder.likecnt = likecnt;
    builder.liked = liked;
    builder.addUnknownFields(unknownFields());
    return builder;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof PBTrends)) return false;
    PBTrends o = (PBTrends) other;
    return Internal.equals(unknownFields(), o.unknownFields())
        && Internal.equals(id, o.id)
        && Internal.equals(uid, o.uid)
        && Internal.equals(contents, o.contents)
        && Internal.equals(view, o.view)
        && Internal.equals(purpose, o.purpose)
        && Internal.equals(pubtime, o.pubtime)
        && Internal.equals(pubpostion, o.pubpostion)
        && Internal.equals(likecnt, o.likecnt)
        && Internal.equals(liked, o.liked);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode;
    if (result == 0) {
      result = unknownFields().hashCode();
      result = result * 37 + (id != null ? id.hashCode() : 0);
      result = result * 37 + (uid != null ? uid.hashCode() : 0);
      result = result * 37 + (contents != null ? contents.hashCode() : 1);
      result = result * 37 + (view != null ? view.hashCode() : 0);
      result = result * 37 + (purpose != null ? purpose.hashCode() : 0);
      result = result * 37 + (pubtime != null ? pubtime.hashCode() : 0);
      result = result * 37 + (pubpostion != null ? pubpostion.hashCode() : 0);
      result = result * 37 + (likecnt != null ? likecnt.hashCode() : 0);
      result = result * 37 + (liked != null ? liked.hashCode() : 0);
      super.hashCode = result;
    }
    return result;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (id != null) builder.append(", id=").append(id);
    if (uid != null) builder.append(", uid=").append(uid);
    if (contents != null) builder.append(", contents=").append(contents);
    if (view != null) builder.append(", view=").append(view);
    if (purpose != null) builder.append(", purpose=").append(purpose);
    if (pubtime != null) builder.append(", pubtime=").append(pubtime);
    if (pubpostion != null) builder.append(", pubpostion=").append(pubpostion);
    if (likecnt != null) builder.append(", likecnt=").append(likecnt);
    if (liked != null) builder.append(", liked=").append(liked);
    return builder.replace(0, 2, "PBTrends{").append('}').toString();
  }

  public static final class Builder extends Message.Builder<PBTrends, Builder> {
    public Long id;

    public Long uid;

    public List<PBMedia> contents;

    public Integer view;

    public Integer purpose;

    public Long pubtime;

    public PBMapPostion pubpostion;

    public Integer likecnt;

    public Integer liked;

    public Builder() {
      contents = Internal.newMutableList();
    }

    /**
     * 	��̬id(�ɷ���˷���)
     */
    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    /**
     * 	��̬�ķ�����
     */
    public Builder uid(Long uid) {
      this.uid = uid;
      return this;
    }

    /**
     * 	��̬����
     */
    public Builder contents(List<PBMedia> contents) {
      Internal.checkElementsNotNull(contents);
      this.contents = contents;
      return this;
    }

    /**
     * 	��̬�Ŀɼ���(PBTrendsView)
     */
    public Builder view(Integer view) {
      this.view = view;
      return this;
    }

    /**
     * ��̬��Ŀ��,PBTrendsPurpose
     */
    public Builder purpose(Integer purpose) {
      this.purpose = purpose;
      return this;
    }

    /**
     * 	����ʱ��
     */
    public Builder pubtime(Long pubtime) {
      this.pubtime = pubtime;
      return this;
    }

    /**
     * 	�����ص�
     */
    public Builder pubpostion(PBMapPostion pubpostion) {
      this.pubpostion = pubpostion;
      return this;
    }

    /**
     * ���޵�����
     */
    public Builder likecnt(Integer likecnt) {
      this.likecnt = likecnt;
      return this;
    }

    /**
     * ��ǰ�û��Ƿ����
     */
    public Builder liked(Integer liked) {
      this.liked = liked;
      return this;
    }

    @Override
    public PBTrends build() {
      if (id == null) {
        throw Internal.missingRequiredFields(id, "id");
      }
      return new PBTrends(id, uid, contents, view, purpose, pubtime, pubpostion, likecnt, liked, buildUnknownFields());
    }
  }

  private static final class ProtoAdapter_PBTrends extends ProtoAdapter<PBTrends> {
    ProtoAdapter_PBTrends() {
      super(FieldEncoding.LENGTH_DELIMITED, PBTrends.class);
    }

    @Override
    public int encodedSize(PBTrends value) {
      return ProtoAdapter.UINT64.encodedSizeWithTag(1, value.id)
          + (value.uid != null ? ProtoAdapter.UINT64.encodedSizeWithTag(2, value.uid) : 0)
          + PBMedia.ADAPTER.asRepeated().encodedSizeWithTag(3, value.contents)
          + (value.view != null ? ProtoAdapter.INT32.encodedSizeWithTag(4, value.view) : 0)
          + (value.purpose != null ? ProtoAdapter.INT32.encodedSizeWithTag(5, value.purpose) : 0)
          + (value.pubtime != null ? ProtoAdapter.UINT64.encodedSizeWithTag(6, value.pubtime) : 0)
          + (value.pubpostion != null ? PBMapPostion.ADAPTER.encodedSizeWithTag(7, value.pubpostion) : 0)
          + (value.likecnt != null ? ProtoAdapter.UINT32.encodedSizeWithTag(8, value.likecnt) : 0)
          + (value.liked != null ? ProtoAdapter.UINT32.encodedSizeWithTag(9, value.liked) : 0)
          + value.unknownFields().size();
    }

    @Override
    public void encode(ProtoWriter writer, PBTrends value) throws IOException {
      ProtoAdapter.UINT64.encodeWithTag(writer, 1, value.id);
      if (value.uid != null) ProtoAdapter.UINT64.encodeWithTag(writer, 2, value.uid);
      if (value.contents != null) PBMedia.ADAPTER.asRepeated().encodeWithTag(writer, 3, value.contents);
      if (value.view != null) ProtoAdapter.INT32.encodeWithTag(writer, 4, value.view);
      if (value.purpose != null) ProtoAdapter.INT32.encodeWithTag(writer, 5, value.purpose);
      if (value.pubtime != null) ProtoAdapter.UINT64.encodeWithTag(writer, 6, value.pubtime);
      if (value.pubpostion != null) PBMapPostion.ADAPTER.encodeWithTag(writer, 7, value.pubpostion);
      if (value.likecnt != null) ProtoAdapter.UINT32.encodeWithTag(writer, 8, value.likecnt);
      if (value.liked != null) ProtoAdapter.UINT32.encodeWithTag(writer, 9, value.liked);
      writer.writeBytes(value.unknownFields());
    }

    @Override
    public PBTrends decode(ProtoReader reader) throws IOException {
      Builder builder = new Builder();
      long token = reader.beginMessage();
      for (int tag; (tag = reader.nextTag()) != -1;) {
        switch (tag) {
          case 1: builder.id(ProtoAdapter.UINT64.decode(reader)); break;
          case 2: builder.uid(ProtoAdapter.UINT64.decode(reader)); break;
          case 3: builder.contents.add(PBMedia.ADAPTER.decode(reader)); break;
          case 4: builder.view(ProtoAdapter.INT32.decode(reader)); break;
          case 5: builder.purpose(ProtoAdapter.INT32.decode(reader)); break;
          case 6: builder.pubtime(ProtoAdapter.UINT64.decode(reader)); break;
          case 7: builder.pubpostion(PBMapPostion.ADAPTER.decode(reader)); break;
          case 8: builder.likecnt(ProtoAdapter.UINT32.decode(reader)); break;
          case 9: builder.liked(ProtoAdapter.UINT32.decode(reader)); break;
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
    public PBTrends redact(PBTrends value) {
      Builder builder = value.newBuilder();
      Internal.redactElements(builder.contents, PBMedia.ADAPTER);
      if (builder.pubpostion != null) builder.pubpostion = PBMapPostion.ADAPTER.redact(builder.pubpostion);
      builder.clearUnknownFields();
      return builder.build();
    }
  }
}