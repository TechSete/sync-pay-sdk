package tech.techsete.sync_pay_sdk.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> implements Serializable {

    @JsonProperty("data")
    private List<T> data;

    @JsonProperty("meta")
    private Meta meta;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Meta {

        @JsonProperty("currentPage")
        private Integer currentPage;

        @JsonProperty("per_page")
        private Integer perPage;

        @JsonProperty("has_more_pages")
        private boolean hasMorePages;
    }
}