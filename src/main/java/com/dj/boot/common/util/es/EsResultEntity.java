package com.dj.boot.common.util.es;

import java.util.List;
import java.util.Map;
public class EsResultEntity {

    private Shards _shards;

    private Hits hits;

    private String took;

    private String timed_out;

    private Aggregations aggregations;

    public Shards get_shards() {
        return _shards;
    }

    public void set_shards(Shards _shards) {
        this._shards = _shards;
    }

    public Hits getHits() {
        return hits;
    }

    public void setHits(Hits hits) {
        this.hits = hits;
    }

    public String getTook() {
        return took;
    }

    public void setTook(String took) {
        this.took = took;
    }

    public String getTimed_out() {
        return timed_out;
    }

    public void setTimed_out(String timed_out) {
        this.timed_out = timed_out;
    }

    public Aggregations getAggregations() {
        return aggregations;
    }

    public void setAggregations(Aggregations aggregations) {
        this.aggregations = aggregations;
    }

    public class Shards{

        private String total;
        private String failed;
        private String successful;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getFailed() {
            return failed;
        }

        public void setFailed(String failed) {
            this.failed = failed;
        }

        public String getSuccessful() {
            return successful;
        }

        public void setSuccessful(String successful) {
            this.successful = successful;
        }
    }

    public class Aggregations{
        private Aggregation aggColumns;

        public Aggregation getAggColumns() {
            return aggColumns;
        }

        public void setAggColumns(Aggregation aggColumns) {
            this.aggColumns = aggColumns;
        }
    }

    public class Aggregation{
        private int doc_count_error_upper_bound;
        private int sum_other_doc_count;
        private List<Map<String, String>> buckets;

        public int getDoc_count_error_upper_bound() {
            return doc_count_error_upper_bound;
        }

        public void setDoc_count_error_upper_bound(int doc_count_error_upper_bound) {
            this.doc_count_error_upper_bound = doc_count_error_upper_bound;
        }

        public int getSum_other_doc_count() {
            return sum_other_doc_count;
        }

        public void setSum_other_doc_count(int sum_other_doc_count) {
            this.sum_other_doc_count = sum_other_doc_count;
        }

        public List<Map<String, String>> getBuckets() {
            return buckets;
        }

        public void setBuckets(List<Map<String, String>> buckets) {
            this.buckets = buckets;
        }
    }

    public class Hits{

        private List<Record> hits;
        private int total;
        private String max_score;

        public List<Record> getHits() {
            return hits;
        }

        public void setHits(List<Record> hits) {
            this.hits = hits;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getMax_score() {
            return max_score;
        }

        public void setMax_score(String max_score) {
            this.max_score = max_score;
        }
    }

    public class Record{
        private String _index;
        private String _type;
        private String _id;
        private String _score;
        private Map<String,String> _source;

        public String get_index() {
            return _index;
        }

        public void set_index(String _index) {
            this._index = _index;
        }

        public String get_type() {
            return _type;
        }

        public void set_type(String _type) {
            this._type = _type;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String get_score() {
            return _score;
        }

        public void set_score(String _score) {
            this._score = _score;
        }

        public Map<String, String> get_source() {
            return _source;
        }

        public void set_source(Map<String, String> _source) {
            this._source = _source;
        }
    }
}

