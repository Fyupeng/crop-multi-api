package com.crop.pojo;

import java.util.ArrayList;
import java.util.Date;
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

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("user_id like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("user_id not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andPicturePathIsNull() {
            addCriterion("picture_path is null");
            return (Criteria) this;
        }

        public Criteria andPicturePathIsNotNull() {
            addCriterion("picture_path is not null");
            return (Criteria) this;
        }

        public Criteria andPicturePathEqualTo(String value) {
            addCriterion("picture_path =", value, "picturePath");
            return (Criteria) this;
        }

        public Criteria andPicturePathNotEqualTo(String value) {
            addCriterion("picture_path <>", value, "picturePath");
            return (Criteria) this;
        }

        public Criteria andPicturePathGreaterThan(String value) {
            addCriterion("picture_path >", value, "picturePath");
            return (Criteria) this;
        }

        public Criteria andPicturePathGreaterThanOrEqualTo(String value) {
            addCriterion("picture_path >=", value, "picturePath");
            return (Criteria) this;
        }

        public Criteria andPicturePathLessThan(String value) {
            addCriterion("picture_path <", value, "picturePath");
            return (Criteria) this;
        }

        public Criteria andPicturePathLessThanOrEqualTo(String value) {
            addCriterion("picture_path <=", value, "picturePath");
            return (Criteria) this;
        }

        public Criteria andPicturePathLike(String value) {
            addCriterion("picture_path like", value, "picturePath");
            return (Criteria) this;
        }

        public Criteria andPicturePathNotLike(String value) {
            addCriterion("picture_path not like", value, "picturePath");
            return (Criteria) this;
        }

        public Criteria andPicturePathIn(List<String> values) {
            addCriterion("picture_path in", values, "picturePath");
            return (Criteria) this;
        }

        public Criteria andPicturePathNotIn(List<String> values) {
            addCriterion("picture_path not in", values, "picturePath");
            return (Criteria) this;
        }

        public Criteria andPicturePathBetween(String value1, String value2) {
            addCriterion("picture_path between", value1, value2, "picturePath");
            return (Criteria) this;
        }

        public Criteria andPicturePathNotBetween(String value1, String value2) {
            addCriterion("picture_path not between", value1, value2, "picturePath");
            return (Criteria) this;
        }

        public Criteria andPictureDescIsNull() {
            addCriterion("picture_desc is null");
            return (Criteria) this;
        }

        public Criteria andPictureDescIsNotNull() {
            addCriterion("picture_desc is not null");
            return (Criteria) this;
        }

        public Criteria andPictureDescEqualTo(String value) {
            addCriterion("picture_desc =", value, "pictureDesc");
            return (Criteria) this;
        }

        public Criteria andPictureDescNotEqualTo(String value) {
            addCriterion("picture_desc <>", value, "pictureDesc");
            return (Criteria) this;
        }

        public Criteria andPictureDescGreaterThan(String value) {
            addCriterion("picture_desc >", value, "pictureDesc");
            return (Criteria) this;
        }

        public Criteria andPictureDescGreaterThanOrEqualTo(String value) {
            addCriterion("picture_desc >=", value, "pictureDesc");
            return (Criteria) this;
        }

        public Criteria andPictureDescLessThan(String value) {
            addCriterion("picture_desc <", value, "pictureDesc");
            return (Criteria) this;
        }

        public Criteria andPictureDescLessThanOrEqualTo(String value) {
            addCriterion("picture_desc <=", value, "pictureDesc");
            return (Criteria) this;
        }

        public Criteria andPictureDescLike(String value) {
            addCriterion("picture_desc like", value, "pictureDesc");
            return (Criteria) this;
        }

        public Criteria andPictureDescNotLike(String value) {
            addCriterion("picture_desc not like", value, "pictureDesc");
            return (Criteria) this;
        }

        public Criteria andPictureDescIn(List<String> values) {
            addCriterion("picture_desc in", values, "pictureDesc");
            return (Criteria) this;
        }

        public Criteria andPictureDescNotIn(List<String> values) {
            addCriterion("picture_desc not in", values, "pictureDesc");
            return (Criteria) this;
        }

        public Criteria andPictureDescBetween(String value1, String value2) {
            addCriterion("picture_desc between", value1, value2, "pictureDesc");
            return (Criteria) this;
        }

        public Criteria andPictureDescNotBetween(String value1, String value2) {
            addCriterion("picture_desc not between", value1, value2, "pictureDesc");
            return (Criteria) this;
        }

        public Criteria andPictureWidthIsNull() {
            addCriterion("picture_width is null");
            return (Criteria) this;
        }

        public Criteria andPictureWidthIsNotNull() {
            addCriterion("picture_width is not null");
            return (Criteria) this;
        }

        public Criteria andPictureWidthEqualTo(Double value) {
            addCriterion("picture_width =", value, "pictureWidth");
            return (Criteria) this;
        }

        public Criteria andPictureWidthNotEqualTo(Double value) {
            addCriterion("picture_width <>", value, "pictureWidth");
            return (Criteria) this;
        }

        public Criteria andPictureWidthGreaterThan(Double value) {
            addCriterion("picture_width >", value, "pictureWidth");
            return (Criteria) this;
        }

        public Criteria andPictureWidthGreaterThanOrEqualTo(Double value) {
            addCriterion("picture_width >=", value, "pictureWidth");
            return (Criteria) this;
        }

        public Criteria andPictureWidthLessThan(Double value) {
            addCriterion("picture_width <", value, "pictureWidth");
            return (Criteria) this;
        }

        public Criteria andPictureWidthLessThanOrEqualTo(Double value) {
            addCriterion("picture_width <=", value, "pictureWidth");
            return (Criteria) this;
        }

        public Criteria andPictureWidthIn(List<Double> values) {
            addCriterion("picture_width in", values, "pictureWidth");
            return (Criteria) this;
        }

        public Criteria andPictureWidthNotIn(List<Double> values) {
            addCriterion("picture_width not in", values, "pictureWidth");
            return (Criteria) this;
        }

        public Criteria andPictureWidthBetween(Double value1, Double value2) {
            addCriterion("picture_width between", value1, value2, "pictureWidth");
            return (Criteria) this;
        }

        public Criteria andPictureWidthNotBetween(Double value1, Double value2) {
            addCriterion("picture_width not between", value1, value2, "pictureWidth");
            return (Criteria) this;
        }

        public Criteria andPictureHeightIsNull() {
            addCriterion("picture_height is null");
            return (Criteria) this;
        }

        public Criteria andPictureHeightIsNotNull() {
            addCriterion("picture_height is not null");
            return (Criteria) this;
        }

        public Criteria andPictureHeightEqualTo(Double value) {
            addCriterion("picture_height =", value, "pictureHeight");
            return (Criteria) this;
        }

        public Criteria andPictureHeightNotEqualTo(Double value) {
            addCriterion("picture_height <>", value, "pictureHeight");
            return (Criteria) this;
        }

        public Criteria andPictureHeightGreaterThan(Double value) {
            addCriterion("picture_height >", value, "pictureHeight");
            return (Criteria) this;
        }

        public Criteria andPictureHeightGreaterThanOrEqualTo(Double value) {
            addCriterion("picture_height >=", value, "pictureHeight");
            return (Criteria) this;
        }

        public Criteria andPictureHeightLessThan(Double value) {
            addCriterion("picture_height <", value, "pictureHeight");
            return (Criteria) this;
        }

        public Criteria andPictureHeightLessThanOrEqualTo(Double value) {
            addCriterion("picture_height <=", value, "pictureHeight");
            return (Criteria) this;
        }

        public Criteria andPictureHeightIn(List<Double> values) {
            addCriterion("picture_height in", values, "pictureHeight");
            return (Criteria) this;
        }

        public Criteria andPictureHeightNotIn(List<Double> values) {
            addCriterion("picture_height not in", values, "pictureHeight");
            return (Criteria) this;
        }

        public Criteria andPictureHeightBetween(Double value1, Double value2) {
            addCriterion("picture_height between", value1, value2, "pictureHeight");
            return (Criteria) this;
        }

        public Criteria andPictureHeightNotBetween(Double value1, Double value2) {
            addCriterion("picture_height not between", value1, value2, "pictureHeight");
            return (Criteria) this;
        }

        public Criteria andUploadTimeIsNull() {
            addCriterion("upload_time is null");
            return (Criteria) this;
        }

        public Criteria andUploadTimeIsNotNull() {
            addCriterion("upload_time is not null");
            return (Criteria) this;
        }

        public Criteria andUploadTimeEqualTo(Date value) {
            addCriterion("upload_time =", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeNotEqualTo(Date value) {
            addCriterion("upload_time <>", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeGreaterThan(Date value) {
            addCriterion("upload_time >", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("upload_time >=", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeLessThan(Date value) {
            addCriterion("upload_time <", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeLessThanOrEqualTo(Date value) {
            addCriterion("upload_time <=", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeIn(List<Date> values) {
            addCriterion("upload_time in", values, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeNotIn(List<Date> values) {
            addCriterion("upload_time not in", values, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeBetween(Date value1, Date value2) {
            addCriterion("upload_time between", value1, value2, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeNotBetween(Date value1, Date value2) {
            addCriterion("upload_time not between", value1, value2, "uploadTime");
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