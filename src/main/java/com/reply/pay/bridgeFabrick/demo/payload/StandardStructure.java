package com.reply.pay.bridgeFabrick.demo.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@RequiredArgsConstructor
public class StandardStructure<T> {

    final private ArrayList<T> list;
    private Pagination pagination;
    private Segmentation segmentation;
    private Sorting sorting;

    private static class Pagination {
        @JsonProperty("pageCount")
        private int getPageCount() {
            return this.pageCount;
        }

        private void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        int pageCount;

        @JsonProperty("resultCount")
        private int getResultCount() {
            return this.resultCount;
        }

        private void setResultCount(int resultCount) {
            this.resultCount = resultCount;
        }

        int resultCount;

        @JsonProperty("offset")
        private int getOffset() {
            return this.offset;
        }

        private void setOffset(int offset) {
            this.offset = offset;
        }

        int offset;

        @JsonProperty("limit")
        private int getLimit() {
            return this.limit;
        }

        private void setLimit(int limit) {
            this.limit = limit;
        }

        int limit;
    }

    private static class Segmentation {
        @JsonProperty("segmentLength")
        private int getSegmentLength() {
            return this.segmentLength;
        }

        private void setSegmentLength(int segmentLength) {
            this.segmentLength = segmentLength;
        }

        int segmentLength;

        @JsonProperty("segmentId")
        private String getSegmentId() {
            return this.segmentId;
        }

        private void setSegmentId(String segmentId) {
            this.segmentId = segmentId;
        }

        String segmentId;

        @JsonProperty("nextSegmentId")
        private String getNextSegmentId() {
            return this.nextSegmentId;
        }

        private void setNextSegmentId(String nextSegmentId) {
            this.nextSegmentId = nextSegmentId;
        }

        String nextSegmentId;
    }

    private static class Sorting {
        @JsonProperty("fieldName")
        private String getFieldName() {
            return this.fieldName;
        }

        private void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        String fieldName;

        @JsonProperty("type")
        private String getType() {
            return this.type;
        }

        private void setType(String type) {
            this.type = type;
        }

        String type;
    }
}