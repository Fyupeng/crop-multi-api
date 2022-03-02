package com.crop.pojo;

import java.util.ArrayList;
import java.util.List;

public class PictureExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PictureExample() {
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

        public Criteria andPictureidIsNull() {
            addCriterion("pictureid is null");
            return (Criteria) this;
        }

        public Criteria andPictureidIsNotNull() {
            addCriterion("pictureid is not null");
            return (Criteria) this;
        }

        public Criteria andPictureidEqualTo(Integer value) {
            addCriterion("pictureid =", value, "pictureid");
            return (Criteria) this;
        }

        public Criteria andPictureidNotEqualTo(Integer value) {
            addCriterion("pictureid <>", value, "pictureid");
            return (Criteria) this;
        }

        public Criteria andPictureidGreaterThan(Integer value) {
            addCriterion("pictureid >", value, "pictureid");
            return (Criteria) this;
        }

        public Criteria andPictureidGreaterThanOrEqualTo(Integer value) {
            addCriterion("pictureid >=", value, "pictureid");
            return (Criteria) this;
        }

        public Criteria andPictureidLessThan(Integer value) {
            addCriterion("pictureid <", value, "pictureid");
            return (Criteria) this;
        }

        public Criteria andPictureidLessThanOrEqualTo(Integer value) {
            addCriterion("pictureid <=", value, "pictureid");
            return (Criteria) this;
        }

        public Criteria andPictureidIn(List<Integer> values) {
            addCriterion("pictureid in", values, "pictureid");
            return (Criteria) this;
        }

        public Criteria andPictureidNotIn(List<Integer> values) {
            addCriterion("pictureid not in", values, "pictureid");
            return (Criteria) this;
        }

        public Criteria andPictureidBetween(Integer value1, Integer value2) {
            addCriterion("pictureid between", value1, value2, "pictureid");
            return (Criteria) this;
        }

        public Criteria andPictureidNotBetween(Integer value1, Integer value2) {
            addCriterion("pictureid not between", value1, value2, "pictureid");
            return (Criteria) this;
        }

        public Criteria andPicturenameIsNull() {
            addCriterion("picturename is null");
            return (Criteria) this;
        }

        public Criteria andPicturenameIsNotNull() {
            addCriterion("picturename is not null");
            return (Criteria) this;
        }

        public Criteria andPicturenameEqualTo(String value) {
            addCriterion("picturename =", value, "picturename");
            return (Criteria) this;
        }

        public Criteria andPicturenameNotEqualTo(String value) {
            addCriterion("picturename <>", value, "picturename");
            return (Criteria) this;
        }

        public Criteria andPicturenameGreaterThan(String value) {
            addCriterion("picturename >", value, "picturename");
            return (Criteria) this;
        }

        public Criteria andPicturenameGreaterThanOrEqualTo(String value) {
            addCriterion("picturename >=", value, "picturename");
            return (Criteria) this;
        }

        public Criteria andPicturenameLessThan(String value) {
            addCriterion("picturename <", value, "picturename");
            return (Criteria) this;
        }

        public Criteria andPicturenameLessThanOrEqualTo(String value) {
            addCriterion("picturename <=", value, "picturename");
            return (Criteria) this;
        }

        public Criteria andPicturenameLike(String value) {
            addCriterion("picturename like", value, "picturename");
            return (Criteria) this;
        }

        public Criteria andPicturenameNotLike(String value) {
            addCriterion("picturename not like", value, "picturename");
            return (Criteria) this;
        }

        public Criteria andPicturenameIn(List<String> values) {
            addCriterion("picturename in", values, "picturename");
            return (Criteria) this;
        }

        public Criteria andPicturenameNotIn(List<String> values) {
            addCriterion("picturename not in", values, "picturename");
            return (Criteria) this;
        }

        public Criteria andPicturenameBetween(String value1, String value2) {
            addCriterion("picturename between", value1, value2, "picturename");
            return (Criteria) this;
        }

        public Criteria andPicturenameNotBetween(String value1, String value2) {
            addCriterion("picturename not between", value1, value2, "picturename");
            return (Criteria) this;
        }

        public Criteria andPicturerouteIsNull() {
            addCriterion("pictureroute is null");
            return (Criteria) this;
        }

        public Criteria andPicturerouteIsNotNull() {
            addCriterion("pictureroute is not null");
            return (Criteria) this;
        }

        public Criteria andPicturerouteEqualTo(String value) {
            addCriterion("pictureroute =", value, "pictureroute");
            return (Criteria) this;
        }

        public Criteria andPicturerouteNotEqualTo(String value) {
            addCriterion("pictureroute <>", value, "pictureroute");
            return (Criteria) this;
        }

        public Criteria andPicturerouteGreaterThan(String value) {
            addCriterion("pictureroute >", value, "pictureroute");
            return (Criteria) this;
        }

        public Criteria andPicturerouteGreaterThanOrEqualTo(String value) {
            addCriterion("pictureroute >=", value, "pictureroute");
            return (Criteria) this;
        }

        public Criteria andPicturerouteLessThan(String value) {
            addCriterion("pictureroute <", value, "pictureroute");
            return (Criteria) this;
        }

        public Criteria andPicturerouteLessThanOrEqualTo(String value) {
            addCriterion("pictureroute <=", value, "pictureroute");
            return (Criteria) this;
        }

        public Criteria andPicturerouteLike(String value) {
            addCriterion("pictureroute like", value, "pictureroute");
            return (Criteria) this;
        }

        public Criteria andPicturerouteNotLike(String value) {
            addCriterion("pictureroute not like", value, "pictureroute");
            return (Criteria) this;
        }

        public Criteria andPicturerouteIn(List<String> values) {
            addCriterion("pictureroute in", values, "pictureroute");
            return (Criteria) this;
        }

        public Criteria andPicturerouteNotIn(List<String> values) {
            addCriterion("pictureroute not in", values, "pictureroute");
            return (Criteria) this;
        }

        public Criteria andPicturerouteBetween(String value1, String value2) {
            addCriterion("pictureroute between", value1, value2, "pictureroute");
            return (Criteria) this;
        }

        public Criteria andPicturerouteNotBetween(String value1, String value2) {
            addCriterion("pictureroute not between", value1, value2, "pictureroute");
            return (Criteria) this;
        }

        public Criteria andUseridIsNull() {
            addCriterion("userid is null");
            return (Criteria) this;
        }

        public Criteria andUseridIsNotNull() {
            addCriterion("userid is not null");
            return (Criteria) this;
        }

        public Criteria andUseridEqualTo(Integer value) {
            addCriterion("userid =", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotEqualTo(Integer value) {
            addCriterion("userid <>", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThan(Integer value) {
            addCriterion("userid >", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThanOrEqualTo(Integer value) {
            addCriterion("userid >=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThan(Integer value) {
            addCriterion("userid <", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThanOrEqualTo(Integer value) {
            addCriterion("userid <=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridIn(List<Integer> values) {
            addCriterion("userid in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotIn(List<Integer> values) {
            addCriterion("userid not in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridBetween(Integer value1, Integer value2) {
            addCriterion("userid between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotBetween(Integer value1, Integer value2) {
            addCriterion("userid not between", value1, value2, "userid");
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