// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Log.proto

package dk.iha.iioss;

public final class Log {
  private Log() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public static final class Entry extends
      com.google.protobuf.GeneratedMessage {
    // Use Entry.newBuilder() to construct.
    private Entry() {
      initFields();
    }
    private Entry(boolean noInit) {}
    
    private static final Entry defaultInstance;
    public static Entry getDefaultInstance() {
      return defaultInstance;
    }
    
    public Entry getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return dk.iha.iioss.Log.internal_static_dk_iha_iioss_Entry_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return dk.iha.iioss.Log.internal_static_dk_iha_iioss_Entry_fieldAccessorTable;
    }
    
    // required sint64 timestamp = 1;
    public static final int TIMESTAMP_FIELD_NUMBER = 1;
    private boolean hasTimestamp;
    private long timestamp_ = 0L;
    public boolean hasTimestamp() { return hasTimestamp; }
    public long getTimestamp() { return timestamp_; }
    
    // optional string rfid = 2;
    public static final int RFID_FIELD_NUMBER = 2;
    private boolean hasRfid;
    private java.lang.String rfid_ = "";
    public boolean hasRfid() { return hasRfid; }
    public java.lang.String getRfid() { return rfid_; }
    
    // optional float weight = 3;
    public static final int WEIGHT_FIELD_NUMBER = 3;
    private boolean hasWeight;
    private float weight_ = 0F;
    public boolean hasWeight() { return hasWeight; }
    public float getWeight() { return weight_; }
    
    private void initFields() {
    }
    public final boolean isInitialized() {
      if (!hasTimestamp) return false;
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (hasTimestamp()) {
        output.writeSInt64(1, getTimestamp());
      }
      if (hasRfid()) {
        output.writeString(2, getRfid());
      }
      if (hasWeight()) {
        output.writeFloat(3, getWeight());
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (hasTimestamp()) {
        size += com.google.protobuf.CodedOutputStream
          .computeSInt64Size(1, getTimestamp());
      }
      if (hasRfid()) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(2, getRfid());
      }
      if (hasWeight()) {
        size += com.google.protobuf.CodedOutputStream
          .computeFloatSize(3, getWeight());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    public static dk.iha.iioss.Log.Entry parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static dk.iha.iioss.Log.Entry parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static dk.iha.iioss.Log.Entry parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static dk.iha.iioss.Log.Entry parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static dk.iha.iioss.Log.Entry parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static dk.iha.iioss.Log.Entry parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static dk.iha.iioss.Log.Entry parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static dk.iha.iioss.Log.Entry parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static dk.iha.iioss.Log.Entry parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static dk.iha.iioss.Log.Entry parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(dk.iha.iioss.Log.Entry prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private dk.iha.iioss.Log.Entry result;
      
      // Construct using dk.iha.iioss.Log.Entry.newBuilder()
      private Builder() {}
      
      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new dk.iha.iioss.Log.Entry();
        return builder;
      }
      
      protected dk.iha.iioss.Log.Entry internalGetResult() {
        return result;
      }
      
      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException(
            "Cannot call clear() after build().");
        }
        result = new dk.iha.iioss.Log.Entry();
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(result);
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return dk.iha.iioss.Log.Entry.getDescriptor();
      }
      
      public dk.iha.iioss.Log.Entry getDefaultInstanceForType() {
        return dk.iha.iioss.Log.Entry.getDefaultInstance();
      }
      
      public boolean isInitialized() {
        return result.isInitialized();
      }
      public dk.iha.iioss.Log.Entry build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }
      
      private dk.iha.iioss.Log.Entry buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }
      
      public dk.iha.iioss.Log.Entry buildPartial() {
        if (result == null) {
          throw new IllegalStateException(
            "build() has already been called on this Builder.");
        }
        dk.iha.iioss.Log.Entry returnMe = result;
        result = null;
        return returnMe;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof dk.iha.iioss.Log.Entry) {
          return mergeFrom((dk.iha.iioss.Log.Entry)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(dk.iha.iioss.Log.Entry other) {
        if (other == dk.iha.iioss.Log.Entry.getDefaultInstance()) return this;
        if (other.hasTimestamp()) {
          setTimestamp(other.getTimestamp());
        }
        if (other.hasRfid()) {
          setRfid(other.getRfid());
        }
        if (other.hasWeight()) {
          setWeight(other.getWeight());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 8: {
              setTimestamp(input.readSInt64());
              break;
            }
            case 18: {
              setRfid(input.readString());
              break;
            }
            case 29: {
              setWeight(input.readFloat());
              break;
            }
          }
        }
      }
      
      
      // required sint64 timestamp = 1;
      public boolean hasTimestamp() {
        return result.hasTimestamp();
      }
      public long getTimestamp() {
        return result.getTimestamp();
      }
      public Builder setTimestamp(long value) {
        result.hasTimestamp = true;
        result.timestamp_ = value;
        return this;
      }
      public Builder clearTimestamp() {
        result.hasTimestamp = false;
        result.timestamp_ = 0L;
        return this;
      }
      
      // optional string rfid = 2;
      public boolean hasRfid() {
        return result.hasRfid();
      }
      public java.lang.String getRfid() {
        return result.getRfid();
      }
      public Builder setRfid(java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  result.hasRfid = true;
        result.rfid_ = value;
        return this;
      }
      public Builder clearRfid() {
        result.hasRfid = false;
        result.rfid_ = getDefaultInstance().getRfid();
        return this;
      }
      
      // optional float weight = 3;
      public boolean hasWeight() {
        return result.hasWeight();
      }
      public float getWeight() {
        return result.getWeight();
      }
      public Builder setWeight(float value) {
        result.hasWeight = true;
        result.weight_ = value;
        return this;
      }
      public Builder clearWeight() {
        result.hasWeight = false;
        result.weight_ = 0F;
        return this;
      }
      
      // @@protoc_insertion_point(builder_scope:dk.iha.iioss.Entry)
    }
    
    static {
      defaultInstance = new Entry(true);
      dk.iha.iioss.Log.internalForceInit();
      defaultInstance.initFields();
    }
    
    // @@protoc_insertion_point(class_scope:dk.iha.iioss.Entry)
  }
  
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_dk_iha_iioss_Entry_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_dk_iha_iioss_Entry_fieldAccessorTable;
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\tLog.proto\022\014dk.iha.iioss\"8\n\005Entry\022\021\n\tti" +
      "mestamp\030\001 \002(\022\022\014\n\004rfid\030\002 \001(\t\022\016\n\006weight\030\003 " +
      "\001(\002"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_dk_iha_iioss_Entry_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_dk_iha_iioss_Entry_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_dk_iha_iioss_Entry_descriptor,
              new java.lang.String[] { "Timestamp", "Rfid", "Weight", },
              dk.iha.iioss.Log.Entry.class,
              dk.iha.iioss.Log.Entry.Builder.class);
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }
  
  public static void internalForceInit() {}
  
  // @@protoc_insertion_point(outer_class_scope)
}
