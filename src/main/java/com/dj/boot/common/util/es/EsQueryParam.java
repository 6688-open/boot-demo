package com.dj.boot.common.util.es;


import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class EsQueryParam {
    private String size;
    private String from;
    private List<Map<String,String>> sort;
    private Query query;
    private Filter filter;
    private Agg aggs;

    public Filter getFilter() {
        if(filter == null){
            return new Filter();
        }
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }
    public List<Map<String, String>> getSort() {
        return sort;
    }

    public void setSort(List<Map<String, String>> sort) {
        this.sort = sort;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Query getQuery() {
        if(query == null){
            query = new Query();
        }
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public Agg getAggs() {
        if(aggs == null){
            aggs = new Agg();
        }
        return aggs;
    }

    public void setAggs(Agg aggs) {
        this.aggs = aggs;
    }

    public class Agg{
        private Map<String, Object> aggColumns;

        public Map<String, Object> getAggColumns() {
            if(aggColumns == null){
                aggColumns = new HashMap<String, Object>();
            }
            return aggColumns;
        }

        public void setAggColumns(Map<String, Object> aggColumns) {
            this.aggColumns = aggColumns;
        }
    }

    public class Query {
        private  Bool bool;

        public Bool getBool() {
            if(bool == null){
                bool = new Bool();
            }
            return bool;
        }

        public void setBool(Bool bool) {
            this.bool = bool;
        }
    }

    public class Bool {
        private List<Must> must;
        private List<Must> must_not;
        private List<Filter> filter;
        public List<Must> getMust() {
            if(must == null){
                must = new LinkedList<Must>();
            }
            return must;
        }
        public List<Must> getMustNot() {
            if(must_not == null){
                must_not = new LinkedList<Must>();
            }
            return must_not;
        }
        public void setMust(List<Must> must) {
            this.must = must;
        }

        public List<Filter> getFilter() {
            if(filter == null){
                filter = new ArrayList<Filter>();
            }
            return filter;
        }

        public void setFilter(List<Filter> filter) {
            this.filter = filter;
        }

        public List<Must> getMust_not() {
            return must_not;
        }

        public void setMust_not(List<Must> must_not) {
            this.must_not = must_not;
        }

        public void addNewTermToMust(String key, Object value){
            Map<String,Object> param = new HashMap<String, Object>();
            param.put(key,value);
            Must must1 = new Must();
            must1.setTerm(param);
            getMust().add(must1);
        }

        public void addNewTermToMustNot(String key, Object value){
            Map<String,Object> param = new HashMap<String, Object>();
            param.put(key,value);
            Must must1 = new Must();
            must1.setTerm(param);
            getMustNot().add(must1);
        }
        public void addGteLteRangeToMust(String key, String beginValue, String endValue){
            Map<String,Range> param = null;
            Must must1 = new Must();
            if(param == null ){
                param = new HashMap<String, Range>();
            }
            Range range = param.get(key);
            if(range == null){
                range = new Range();
            }
            if(StringUtils.isNotBlank(beginValue))
                range.setGte(beginValue);
            if(StringUtils.isNotBlank(endValue))
                range.setLte(endValue);
            param.put(key,range);
            must1.setRange(param);
            getMust().add(must1);
        }

        public void addGtLtRangeToMust(String key,String beginValue,String endValue){
            Map<String,Range> param = null;
            Must must1 = new Must();
            if(param == null ){
                param = new HashMap<String, Range>();
            }
            Range range = param.get(key);
            if(range == null){
                range = new Range();
            }
            if(StringUtils.isNotBlank(beginValue))
                range.setGt(beginValue);
            if(StringUtils.isNotBlank(endValue))
                range.setLt(endValue);
            param.put(key,range);
            must1.setRange(param);
            getMust().add(must1);
        }

        public void addFromToRangeToMust(String key,String beginValue,String endValue){
            Map<String,Range> param = null;
            Must must1 = new Must();
            if(param == null ){
                param = new HashMap<String, Range>();
            }
            Range range = param.get(key);
            if(range == null){
                range = new Range();
            }
            if(StringUtils.isNotBlank(beginValue))
                range.setFrom(beginValue);
            if(StringUtils.isNotBlank(endValue))
                range.setTo(endValue);
            param.put(key,range);
            must1.setRange(param);
            getMust().add(must1);
        }

        public void addNewTermsToFilter(String key,List values){
            Map<String,List> param = new HashMap<String, List>();
            param.put(key,values);
            Filter filter1 = new Filter();
            filter1.setTerms(param);
            getFilter().add(filter1);
        }

        public void addScriptToFilter(String key,String value){
            Map<String,String> param = new HashMap<String, String>();
            param.put(key,value);
            Filter filter1 = new Filter();
            filter1.setScript(param);
            getFilter().add(filter1);
        }

        public void addWildcardToMust(String key, String value){
            Map<String,Object> param = new HashMap<String, Object>();
            param.put(key,value);
            Must must = new Must();
            must.setWildcard(param);
            getMust().add(must);
        }

        public void addBoolToMust(String key, Object value){
            Map<String,Object> param = new HashMap<String, Object>();
            param.put(key,value);
            Must must = new Must();
            must.setBool(param);
            getMust().add(must);
        }
    }

    public class Must {
        private Map<String,Object> term;

        private Map<String,Range> range;

        private Map<String,Object> wildcard;

        private Map<String, Object> bool;

        public Map<String, Range> getRange() {
            if(range == null){
                range =  new HashMap<String, Range>();
            }
            return range;
        }

        public void setRange(Map<String, Range> range) {
            this.range = range;
        }

        public Map<String,Object> getTerm() {
            if(term == null){
                term = new HashMap<String,Object>();
            }
            return term;
        }

        public void setTerm(Map<String,Object> term) {
            this.term = term;
        }

        public void setWildcard(Map<String, Object> wildcard) {
            this.wildcard = wildcard;
        }

        public Map<String, Object> getWildcard() {
            if(wildcard == null){
                wildcard = new HashMap<String, Object>();
            }
            return wildcard;
        }

        public Map<String, Object> getBool() {
            if(bool == null){
                bool = new HashMap<String, Object>();
            }
            return bool;
        }

        public void setBool(Map<String, Object> bool) {
            this.bool = bool;
        }
    }



    public class Or {
        private Map<String,Object> term;

        private Map<String,Range> range;

        private Map<String,Object> terms;
        public Map<String, Range> getRange() {
            if(range == null){
                range =  new HashMap<String, Range>();
            }
            return range;
        }

        public void setRange(Map<String, Range> range) {
            this.range = range;
        }

        public Map<String,Object> getTerm() {
            if(term == null){
                term = new HashMap<String,Object>();
            }
            return term;
        }

        public void setTerm(Map<String,Object> term) {
            this.term = term;
        }

        public Map<String, Object> getTerms() {
            if(terms == null){
                terms = new HashMap<String,Object>();
            }
            return terms;
        }

        public void setTerms(Map<String, Object> terms) {
            this.terms = terms;
        }

    }

    public class Filter {
        private Map<String,List> terms;
        private  Map<String,String> script;
        private  List<Or> or;

        public List<Or> getOr() {
            if(or == null){
                or = new LinkedList<Or>();
            }
            return or;
        }

        public void setOr(List<Or> or) {
            this.or = or;
        }
        public Map<String, String> getScript() {
            if (null == script){
                script = new HashMap<String, String>();
            }
            return script;
        }

        public void setScript(Map<String, String> script) {
            this.script = script;
        }

        public Map<String,List> getTerms() {
            if(terms == null){
                terms = new HashMap<String,List>();
            }
            return terms;
        }

        public void setTerms(Map<String,List> terms) {
            this.terms = terms;
        }

        public void addTermsToOr(String key,Object value){
            Map<String,Object> param = new HashMap<String, Object>();
            param.put(key,value);
            Or or = new Or();
            or.setTerms(param);
            getOr().add(or);
        }
    }

    public class Range{
        private String gt;
        private String lt;
        private String gte;
        private String lte;
        private String from;
        private String to;

        public String getGte() {
            return gte;
        }

        public void setGte(String gte) {
            this.gte = gte;
        }

        public String getLte() {
            return lte;
        }

        public void setLte(String lte) {
            this.lte = lte;
        }

        public String getGt() {
            return gt;
        }

        public void setGt(String gt) {
            this.gt = gt;
        }

        public String getLt() {
            return lt;
        }

        public void setLt(String lt) {
            this.lt = lt;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }
    }


}
