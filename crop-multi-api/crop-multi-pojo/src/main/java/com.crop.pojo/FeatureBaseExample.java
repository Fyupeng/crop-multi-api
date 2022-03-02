package com.crop.pojo;

import java.util.ArrayList;
import java.util.List;

public class FeatureBaseExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FeatureBaseExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andBotanyIdIsNull() {
            addCriterion("botany_id is null");
            return (Criteria) this;
        }

        public Criteria andBotanyIdIsNotNull() {
            addCriterion("botany_id is not null");
            return (Criteria) this;
        }

        public Criteria andBotanyIdEqualTo(String value) {
            addCriterion("botany_id =", value, "botanyId");
            return (Criteria) this;
        }

        public Criteria andBotanyIdNotEqualTo(String value) {
            addCriterion("botany_id <>", value, "botanyId");
            return (Criteria) this;
        }

        public Criteria andBotanyIdGreaterThan(String value) {
            addCriterion("botany_id >", value, "botanyId");
            return (Criteria) this;
        }

        public Criteria andBotanyIdGreaterThanOrEqualTo(String value) {
            addCriterion("botany_id >=", value, "botanyId");
            return (Criteria) this;
        }

        public Criteria andBotanyIdLessThan(String value) {
            addCriterion("botany_id <", value, "botanyId");
            return (Criteria) this;
        }

        public Criteria andBotanyIdLessThanOrEqualTo(String value) {
            addCriterion("botany_id <=", value, "botanyId");
            return (Criteria) this;
        }

        public Criteria andBotanyIdLike(String value) {
            addCriterion("botany_id like", value, "botanyId");
            return (Criteria) this;
        }

        public Criteria andBotanyIdNotLike(String value) {
            addCriterion("botany_id not like", value, "botanyId");
            return (Criteria) this;
        }

        public Criteria andBotanyIdIn(List<String> values) {
            addCriterion("botany_id in", values, "botanyId");
            return (Criteria) this;
        }

        public Criteria andBotanyIdNotIn(List<String> values) {
            addCriterion("botany_id not in", values, "botanyId");
            return (Criteria) this;
        }

        public Criteria andBotanyIdBetween(String value1, String value2) {
            addCriterion("botany_id between", value1, value2, "botanyId");
            return (Criteria) this;
        }

        public Criteria andBotanyIdNotBetween(String value1, String value2) {
            addCriterion("botany_id not between", value1, value2, "botanyId");
            return (Criteria) this;
        }

        public Criteria andPestIdIsNull() {
            addCriterion("pest_id is null");
            return (Criteria) this;
        }

        public Criteria andPestIdIsNotNull() {
            addCriterion("pest_id is not null");
            return (Criteria) this;
        }

        public Criteria andPestIdEqualTo(String value) {
            addCriterion("pest_id =", value, "pestId");
            return (Criteria) this;
        }

        public Criteria andPestIdNotEqualTo(String value) {
            addCriterion("pest_id <>", value, "pestId");
            return (Criteria) this;
        }

        public Criteria andPestIdGreaterThan(String value) {
            addCriterion("pest_id >", value, "pestId");
            return (Criteria) this;
        }

        public Criteria andPestIdGreaterThanOrEqualTo(String value) {
            addCriterion("pest_id >=", value, "pestId");
            return (Criteria) this;
        }

        public Criteria andPestIdLessThan(String value) {
            addCriterion("pest_id <", value, "pestId");
            return (Criteria) this;
        }

        public Criteria andPestIdLessThanOrEqualTo(String value) {
            addCriterion("pest_id <=", value, "pestId");
            return (Criteria) this;
        }

        public Criteria andPestIdLike(String value) {
            addCriterion("pest_id like", value, "pestId");
            return (Criteria) this;
        }

        public Criteria andPestIdNotLike(String value) {
            addCriterion("pest_id not like", value, "pestId");
            return (Criteria) this;
        }

        public Criteria andPestIdIn(List<String> values) {
            addCriterion("pest_id in", values, "pestId");
            return (Criteria) this;
        }

        public Criteria andPestIdNotIn(List<String> values) {
            addCriterion("pest_id not in", values, "pestId");
            return (Criteria) this;
        }

        public Criteria andPestIdBetween(String value1, String value2) {
            addCriterion("pest_id between", value1, value2, "pestId");
            return (Criteria) this;
        }

        public Criteria andPestIdNotBetween(String value1, String value2) {
            addCriterion("pest_id not between", value1, value2, "pestId");
            return (Criteria) this;
        }

        public Criteria andBotanyNameIsNull() {
            addCriterion("botany_name is null");
            return (Criteria) this;
        }

        public Criteria andBotanyNameIsNotNull() {
            addCriterion("botany_name is not null");
            return (Criteria) this;
        }

        public Criteria andBotanyNameEqualTo(String value) {
            addCriterion("botany_name =", value, "botanyName");
            return (Criteria) this;
        }

        public Criteria andBotanyNameNotEqualTo(String value) {
            addCriterion("botany_name <>", value, "botanyName");
            return (Criteria) this;
        }

        public Criteria andBotanyNameGreaterThan(String value) {
            addCriterion("botany_name >", value, "botanyName");
            return (Criteria) this;
        }

        public Criteria andBotanyNameGreaterThanOrEqualTo(String value) {
            addCriterion("botany_name >=", value, "botanyName");
            return (Criteria) this;
        }

        public Criteria andBotanyNameLessThan(String value) {
            addCriterion("botany_name <", value, "botanyName");
            return (Criteria) this;
        }

        public Criteria andBotanyNameLessThanOrEqualTo(String value) {
            addCriterion("botany_name <=", value, "botanyName");
            return (Criteria) this;
        }

        public Criteria andBotanyNameLike(String value) {
            addCriterion("botany_name like", value, "botanyName");
            return (Criteria) this;
        }

        public Criteria andBotanyNameNotLike(String value) {
            addCriterion("botany_name not like", value, "botanyName");
            return (Criteria) this;
        }

        public Criteria andBotanyNameIn(List<String> values) {
            addCriterion("botany_name in", values, "botanyName");
            return (Criteria) this;
        }

        public Criteria andBotanyNameNotIn(List<String> values) {
            addCriterion("botany_name not in", values, "botanyName");
            return (Criteria) this;
        }

        public Criteria andBotanyNameBetween(String value1, String value2) {
            addCriterion("botany_name between", value1, value2, "botanyName");
            return (Criteria) this;
        }

        public Criteria andBotanyNameNotBetween(String value1, String value2) {
            addCriterion("botany_name not between", value1, value2, "botanyName");
            return (Criteria) this;
        }

        public Criteria andPestNameIsNull() {
            addCriterion("pest_name is null");
            return (Criteria) this;
        }

        public Criteria andPestNameIsNotNull() {
            addCriterion("pest_name is not null");
            return (Criteria) this;
        }

        public Criteria andPestNameEqualTo(String value) {
            addCriterion("pest_name =", value, "pestName");
            return (Criteria) this;
        }

        public Criteria andPestNameNotEqualTo(String value) {
            addCriterion("pest_name <>", value, "pestName");
            return (Criteria) this;
        }

        public Criteria andPestNameGreaterThan(String value) {
            addCriterion("pest_name >", value, "pestName");
            return (Criteria) this;
        }

        public Criteria andPestNameGreaterThanOrEqualTo(String value) {
            addCriterion("pest_name >=", value, "pestName");
            return (Criteria) this;
        }

        public Criteria andPestNameLessThan(String value) {
            addCriterion("pest_name <", value, "pestName");
            return (Criteria) this;
        }

        public Criteria andPestNameLessThanOrEqualTo(String value) {
            addCriterion("pest_name <=", value, "pestName");
            return (Criteria) this;
        }

        public Criteria andPestNameLike(String value) {
            addCriterion("pest_name like", value, "pestName");
            return (Criteria) this;
        }

        public Criteria andPestNameNotLike(String value) {
            addCriterion("pest_name not like", value, "pestName");
            return (Criteria) this;
        }

        public Criteria andPestNameIn(List<String> values) {
            addCriterion("pest_name in", values, "pestName");
            return (Criteria) this;
        }

        public Criteria andPestNameNotIn(List<String> values) {
            addCriterion("pest_name not in", values, "pestName");
            return (Criteria) this;
        }

        public Criteria andPestNameBetween(String value1, String value2) {
            addCriterion("pest_name between", value1, value2, "pestName");
            return (Criteria) this;
        }

        public Criteria andPestNameNotBetween(String value1, String value2) {
            addCriterion("pest_name not between", value1, value2, "pestName");
            return (Criteria) this;
        }

        public Criteria andPicPathIsNull() {
            addCriterion("pic_path is null");
            return (Criteria) this;
        }

        public Criteria andPicPathIsNotNull() {
            addCriterion("pic_path is not null");
            return (Criteria) this;
        }

        public Criteria andPicPathEqualTo(String value) {
            addCriterion("pic_path =", value, "picPath");
            return (Criteria) this;
        }

        public Criteria andPicPathNotEqualTo(String value) {
            addCriterion("pic_path <>", value, "picPath");
            return (Criteria) this;
        }

        public Criteria andPicPathGreaterThan(String value) {
            addCriterion("pic_path >", value, "picPath");
            return (Criteria) this;
        }

        public Criteria andPicPathGreaterThanOrEqualTo(String value) {
            addCriterion("pic_path >=", value, "picPath");
            return (Criteria) this;
        }

        public Criteria andPicPathLessThan(String value) {
            addCriterion("pic_path <", value, "picPath");
            return (Criteria) this;
        }

        public Criteria andPicPathLessThanOrEqualTo(String value) {
            addCriterion("pic_path <=", value, "picPath");
            return (Criteria) this;
        }

        public Criteria andPicPathLike(String value) {
            addCriterion("pic_path like", value, "picPath");
            return (Criteria) this;
        }

        public Criteria andPicPathNotLike(String value) {
            addCriterion("pic_path not like", value, "picPath");
            return (Criteria) this;
        }

        public Criteria andPicPathIn(List<String> values) {
            addCriterion("pic_path in", values, "picPath");
            return (Criteria) this;
        }

        public Criteria andPicPathNotIn(List<String> values) {
            addCriterion("pic_path not in", values, "picPath");
            return (Criteria) this;
        }

        public Criteria andPicPathBetween(String value1, String value2) {
            addCriterion("pic_path between", value1, value2, "picPath");
            return (Criteria) this;
        }

        public Criteria andPicPathNotBetween(String value1, String value2) {
            addCriterion("pic_path not between", value1, value2, "picPath");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}