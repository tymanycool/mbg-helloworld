package com.csii.pmis.service.bean.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;;
import java.math.BigDecimal;

/*
 * @description 账户表Example
 * @author tiany
 * @version 2018-10-03 modify: tiany
 * @since 1.0
 */
public class AccountExample implements Serializable{
	/** 序列化号 */
	private static final long serialVersionUID = 2008281769309670272L;
	protected String orderByClause;
	protected boolean distinct;
	protected List<Criteria> oredCriteria;
	public AccountExample() {
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
		public Criteria andAcNoIsNull() {
			addCriterion("ac_no IS NULL");
			return (Criteria) this;
		}
		public Criteria andAcNoIsNotNull() {
			addCriterion("ac_no IS NOT NULL");
			return (Criteria) this;
		}
		public Criteria andAcNoEqualTo(String value) {
			addCriterion("ac_no =", value, "acNo");
			return (Criteria) this;
		}
		public Criteria andAcNoNotEqualTo(String value) {
			addCriterion("ac_no &lt;&gt;", value, "acNo");
			return (Criteria) this;
		}
		public Criteria andAcNoLessThan(String value) {
			addCriterion("ac_no &lt;", value, "acNo");
			return (Criteria) this;
		}
		public Criteria andAcNoLike(String value) {
			addCriterion("ac_no LIKE", value, "acNo");
			return (Criteria) this;
		}
		public Criteria andAcNoNotLike(String value) {
			addCriterion("ac_no NOT LIKE", value, "acNo");
			return (Criteria) this;
		}
		public Criteria andAcNoIn(List<String> values) {
			addCriterion("ac_no IN", values, "acNo");
			return (Criteria) this;
		}
		public Criteria andAcNoNotIn(List<String> values) {
			addCriterion("ac_no NOT IN", values, "acNo");
			return (Criteria) this;
		}
		public Criteria andAcNoBetween(String value1, String value2) {
			addCriterion("ac_no BETWEEN", value1, value2, "acNo");
			return (Criteria) this;
		}
		public Criteria andAcNoNotBetween(String value1, String value2) {
			addCriterion("ac_no NOT BETWEEN", value1, value2, "acNo");
			return (Criteria) this;
		}
		public Criteria andOrderIdIsNull() {
			addCriterion("order_id IS NULL");
			return (Criteria) this;
		}
		public Criteria andOrderIdIsNotNull() {
			addCriterion("order_id IS NOT NULL");
			return (Criteria) this;
		}
		public Criteria andOrderIdEqualTo(String value) {
			addCriterion("order_id =", value, "orderId");
			return (Criteria) this;
		}
		public Criteria andOrderIdNotEqualTo(String value) {
			addCriterion("order_id &lt;&gt;", value, "orderId");
			return (Criteria) this;
		}
		public Criteria andOrderIdLessThan(String value) {
			addCriterion("order_id &lt;", value, "orderId");
			return (Criteria) this;
		}
		public Criteria andOrderIdLike(String value) {
			addCriterion("order_id LIKE", value, "orderId");
			return (Criteria) this;
		}
		public Criteria andOrderIdNotLike(String value) {
			addCriterion("order_id NOT LIKE", value, "orderId");
			return (Criteria) this;
		}
		public Criteria andOrderIdIn(List<String> values) {
			addCriterion("order_id IN", values, "orderId");
			return (Criteria) this;
		}
		public Criteria andOrderIdNotIn(List<String> values) {
			addCriterion("order_id NOT IN", values, "orderId");
			return (Criteria) this;
		}
		public Criteria andOrderIdBetween(String value1, String value2) {
			addCriterion("order_id BETWEEN", value1, value2, "orderId");
			return (Criteria) this;
		}
		public Criteria andOrderIdNotBetween(String value1, String value2) {
			addCriterion("order_id NOT BETWEEN", value1, value2, "orderId");
			return (Criteria) this;
		}
		public Criteria andOrgIdIsNull() {
			addCriterion("org_id IS NULL");
			return (Criteria) this;
		}
		public Criteria andOrgIdIsNotNull() {
			addCriterion("org_id IS NOT NULL");
			return (Criteria) this;
		}
		public Criteria andOrgIdEqualTo(String value) {
			addCriterion("org_id =", value, "orgId");
			return (Criteria) this;
		}
		public Criteria andOrgIdNotEqualTo(String value) {
			addCriterion("org_id &lt;&gt;", value, "orgId");
			return (Criteria) this;
		}
		public Criteria andOrgIdLessThan(String value) {
			addCriterion("org_id &lt;", value, "orgId");
			return (Criteria) this;
		}
		public Criteria andOrgIdLike(String value) {
			addCriterion("org_id LIKE", value, "orgId");
			return (Criteria) this;
		}
		public Criteria andOrgIdNotLike(String value) {
			addCriterion("org_id NOT LIKE", value, "orgId");
			return (Criteria) this;
		}
		public Criteria andOrgIdIn(List<String> values) {
			addCriterion("org_id IN", values, "orgId");
			return (Criteria) this;
		}
		public Criteria andOrgIdNotIn(List<String> values) {
			addCriterion("org_id NOT IN", values, "orgId");
			return (Criteria) this;
		}
		public Criteria andOrgIdBetween(String value1, String value2) {
			addCriterion("org_id BETWEEN", value1, value2, "orgId");
			return (Criteria) this;
		}
		public Criteria andOrgIdNotBetween(String value1, String value2) {
			addCriterion("org_id NOT BETWEEN", value1, value2, "orgId");
			return (Criteria) this;
		}
		public Criteria andCustIdIsNull() {
			addCriterion("cust_id IS NULL");
			return (Criteria) this;
		}
		public Criteria andCustIdIsNotNull() {
			addCriterion("cust_id IS NOT NULL");
			return (Criteria) this;
		}
		public Criteria andCustIdEqualTo(String value) {
			addCriterion("cust_id =", value, "custId");
			return (Criteria) this;
		}
		public Criteria andCustIdNotEqualTo(String value) {
			addCriterion("cust_id &lt;&gt;", value, "custId");
			return (Criteria) this;
		}
		public Criteria andCustIdLessThan(String value) {
			addCriterion("cust_id &lt;", value, "custId");
			return (Criteria) this;
		}
		public Criteria andCustIdLike(String value) {
			addCriterion("cust_id LIKE", value, "custId");
			return (Criteria) this;
		}
		public Criteria andCustIdNotLike(String value) {
			addCriterion("cust_id NOT LIKE", value, "custId");
			return (Criteria) this;
		}
		public Criteria andCustIdIn(List<String> values) {
			addCriterion("cust_id IN", values, "custId");
			return (Criteria) this;
		}
		public Criteria andCustIdNotIn(List<String> values) {
			addCriterion("cust_id NOT IN", values, "custId");
			return (Criteria) this;
		}
		public Criteria andCustIdBetween(String value1, String value2) {
			addCriterion("cust_id BETWEEN", value1, value2, "custId");
			return (Criteria) this;
		}
		public Criteria andCustIdNotBetween(String value1, String value2) {
			addCriterion("cust_id NOT BETWEEN", value1, value2, "custId");
			return (Criteria) this;
		}
		public Criteria andBankIdIsNull() {
			addCriterion("bank_id IS NULL");
			return (Criteria) this;
		}
		public Criteria andBankIdIsNotNull() {
			addCriterion("bank_id IS NOT NULL");
			return (Criteria) this;
		}
		public Criteria andBankIdEqualTo(String value) {
			addCriterion("bank_id =", value, "bankId");
			return (Criteria) this;
		}
		public Criteria andBankIdNotEqualTo(String value) {
			addCriterion("bank_id &lt;&gt;", value, "bankId");
			return (Criteria) this;
		}
		public Criteria andBankIdLessThan(String value) {
			addCriterion("bank_id &lt;", value, "bankId");
			return (Criteria) this;
		}
		public Criteria andBankIdLike(String value) {
			addCriterion("bank_id LIKE", value, "bankId");
			return (Criteria) this;
		}
		public Criteria andBankIdNotLike(String value) {
			addCriterion("bank_id NOT LIKE", value, "bankId");
			return (Criteria) this;
		}
		public Criteria andBankIdIn(List<String> values) {
			addCriterion("bank_id IN", values, "bankId");
			return (Criteria) this;
		}
		public Criteria andBankIdNotIn(List<String> values) {
			addCriterion("bank_id NOT IN", values, "bankId");
			return (Criteria) this;
		}
		public Criteria andBankIdBetween(String value1, String value2) {
			addCriterion("bank_id BETWEEN", value1, value2, "bankId");
			return (Criteria) this;
		}
		public Criteria andBankIdNotBetween(String value1, String value2) {
			addCriterion("bank_id NOT BETWEEN", value1, value2, "bankId");
			return (Criteria) this;
		}
		public Criteria andBankNameIsNull() {
			addCriterion("bank_name IS NULL");
			return (Criteria) this;
		}
		public Criteria andBankNameIsNotNull() {
			addCriterion("bank_name IS NOT NULL");
			return (Criteria) this;
		}
		public Criteria andBankNameEqualTo(String value) {
			addCriterion("bank_name =", value, "bankName");
			return (Criteria) this;
		}
		public Criteria andBankNameNotEqualTo(String value) {
			addCriterion("bank_name &lt;&gt;", value, "bankName");
			return (Criteria) this;
		}
		public Criteria andBankNameLessThan(String value) {
			addCriterion("bank_name &lt;", value, "bankName");
			return (Criteria) this;
		}
		public Criteria andBankNameLike(String value) {
			addCriterion("bank_name LIKE", value, "bankName");
			return (Criteria) this;
		}
		public Criteria andBankNameNotLike(String value) {
			addCriterion("bank_name NOT LIKE", value, "bankName");
			return (Criteria) this;
		}
		public Criteria andBankNameIn(List<String> values) {
			addCriterion("bank_name IN", values, "bankName");
			return (Criteria) this;
		}
		public Criteria andBankNameNotIn(List<String> values) {
			addCriterion("bank_name NOT IN", values, "bankName");
			return (Criteria) this;
		}
		public Criteria andBankNameBetween(String value1, String value2) {
			addCriterion("bank_name BETWEEN", value1, value2, "bankName");
			return (Criteria) this;
		}
		public Criteria andBankNameNotBetween(String value1, String value2) {
			addCriterion("bank_name NOT BETWEEN", value1, value2, "bankName");
			return (Criteria) this;
		}
		public Criteria andAccountTypeIsNull() {
			addCriterion("account_type IS NULL");
			return (Criteria) this;
		}
		public Criteria andAccountTypeIsNotNull() {
			addCriterion("account_type IS NOT NULL");
			return (Criteria) this;
		}
		public Criteria andAccountTypeEqualTo(String value) {
			addCriterion("account_type =", value, "accountType");
			return (Criteria) this;
		}
		public Criteria andAccountTypeNotEqualTo(String value) {
			addCriterion("account_type &lt;&gt;", value, "accountType");
			return (Criteria) this;
		}
		public Criteria andAccountTypeLessThan(String value) {
			addCriterion("account_type &lt;", value, "accountType");
			return (Criteria) this;
		}
		public Criteria andAccountTypeLike(String value) {
			addCriterion("account_type LIKE", value, "accountType");
			return (Criteria) this;
		}
		public Criteria andAccountTypeNotLike(String value) {
			addCriterion("account_type NOT LIKE", value, "accountType");
			return (Criteria) this;
		}
		public Criteria andAccountTypeIn(List<String> values) {
			addCriterion("account_type IN", values, "accountType");
			return (Criteria) this;
		}
		public Criteria andAccountTypeNotIn(List<String> values) {
			addCriterion("account_type NOT IN", values, "accountType");
			return (Criteria) this;
		}
		public Criteria andAccountTypeBetween(String value1, String value2) {
			addCriterion("account_type BETWEEN", value1, value2, "accountType");
			return (Criteria) this;
		}
		public Criteria andAccountTypeNotBetween(String value1, String value2) {
			addCriterion("account_type NOT BETWEEN", value1, value2, "accountType");
			return (Criteria) this;
		}
		public Criteria andBindPhoneIsNull() {
			addCriterion("bind_phone IS NULL");
			return (Criteria) this;
		}
		public Criteria andBindPhoneIsNotNull() {
			addCriterion("bind_phone IS NOT NULL");
			return (Criteria) this;
		}
		public Criteria andBindPhoneEqualTo(String value) {
			addCriterion("bind_phone =", value, "bindPhone");
			return (Criteria) this;
		}
		public Criteria andBindPhoneNotEqualTo(String value) {
			addCriterion("bind_phone &lt;&gt;", value, "bindPhone");
			return (Criteria) this;
		}
		public Criteria andBindPhoneLessThan(String value) {
			addCriterion("bind_phone &lt;", value, "bindPhone");
			return (Criteria) this;
		}
		public Criteria andBindPhoneLike(String value) {
			addCriterion("bind_phone LIKE", value, "bindPhone");
			return (Criteria) this;
		}
		public Criteria andBindPhoneNotLike(String value) {
			addCriterion("bind_phone NOT LIKE", value, "bindPhone");
			return (Criteria) this;
		}
		public Criteria andBindPhoneIn(List<String> values) {
			addCriterion("bind_phone IN", values, "bindPhone");
			return (Criteria) this;
		}
		public Criteria andBindPhoneNotIn(List<String> values) {
			addCriterion("bind_phone NOT IN", values, "bindPhone");
			return (Criteria) this;
		}
		public Criteria andBindPhoneBetween(String value1, String value2) {
			addCriterion("bind_phone BETWEEN", value1, value2, "bindPhone");
			return (Criteria) this;
		}
		public Criteria andBindPhoneNotBetween(String value1, String value2) {
			addCriterion("bind_phone NOT BETWEEN", value1, value2, "bindPhone");
			return (Criteria) this;
		}
		public Criteria andPassWordIsNull() {
			addCriterion("pass_word IS NULL");
			return (Criteria) this;
		}
		public Criteria andPassWordIsNotNull() {
			addCriterion("pass_word IS NOT NULL");
			return (Criteria) this;
		}
		public Criteria andPassWordEqualTo(String value) {
			addCriterion("pass_word =", value, "passWord");
			return (Criteria) this;
		}
		public Criteria andPassWordNotEqualTo(String value) {
			addCriterion("pass_word &lt;&gt;", value, "passWord");
			return (Criteria) this;
		}
		public Criteria andPassWordLessThan(String value) {
			addCriterion("pass_word &lt;", value, "passWord");
			return (Criteria) this;
		}
		public Criteria andPassWordLike(String value) {
			addCriterion("pass_word LIKE", value, "passWord");
			return (Criteria) this;
		}
		public Criteria andPassWordNotLike(String value) {
			addCriterion("pass_word NOT LIKE", value, "passWord");
			return (Criteria) this;
		}
		public Criteria andPassWordIn(List<String> values) {
			addCriterion("pass_word IN", values, "passWord");
			return (Criteria) this;
		}
		public Criteria andPassWordNotIn(List<String> values) {
			addCriterion("pass_word NOT IN", values, "passWord");
			return (Criteria) this;
		}
		public Criteria andPassWordBetween(String value1, String value2) {
			addCriterion("pass_word BETWEEN", value1, value2, "passWord");
			return (Criteria) this;
		}
		public Criteria andPassWordNotBetween(String value1, String value2) {
			addCriterion("pass_word NOT BETWEEN", value1, value2, "passWord");
			return (Criteria) this;
		}
		public Criteria andSerialNoIsNull() {
			addCriterion("serial_no IS NULL");
			return (Criteria) this;
		}
		public Criteria andSerialNoIsNotNull() {
			addCriterion("serial_no IS NOT NULL");
			return (Criteria) this;
		}
		public Criteria andSerialNoEqualTo(String value) {
			addCriterion("serial_no =", value, "serialNo");
			return (Criteria) this;
		}
		public Criteria andSerialNoNotEqualTo(String value) {
			addCriterion("serial_no &lt;&gt;", value, "serialNo");
			return (Criteria) this;
		}
		public Criteria andSerialNoLessThan(String value) {
			addCriterion("serial_no &lt;", value, "serialNo");
			return (Criteria) this;
		}
		public Criteria andSerialNoLike(String value) {
			addCriterion("serial_no LIKE", value, "serialNo");
			return (Criteria) this;
		}
		public Criteria andSerialNoNotLike(String value) {
			addCriterion("serial_no NOT LIKE", value, "serialNo");
			return (Criteria) this;
		}
		public Criteria andSerialNoIn(List<String> values) {
			addCriterion("serial_no IN", values, "serialNo");
			return (Criteria) this;
		}
		public Criteria andSerialNoNotIn(List<String> values) {
			addCriterion("serial_no NOT IN", values, "serialNo");
			return (Criteria) this;
		}
		public Criteria andSerialNoBetween(String value1, String value2) {
			addCriterion("serial_no BETWEEN", value1, value2, "serialNo");
			return (Criteria) this;
		}
		public Criteria andSerialNoNotBetween(String value1, String value2) {
			addCriterion("serial_no NOT BETWEEN", value1, value2, "serialNo");
			return (Criteria) this;
		}
		public Criteria andCreateDateIsNull() {
			addCriterion("create_date IS NULL");
			return (Criteria) this;
		}
		public Criteria andCreateDateIsNotNull() {
			addCriterion("create_date IS NOT NULL");
			return (Criteria) this;
		}
		public Criteria andCreateDateEqualTo(Date value) {
			addCriterion("create_date =", value, "createDate");
			return (Criteria) this;
		}
		public Criteria andCreateDateNotEqualTo(Date value) {
			addCriterion("create_date &lt;&gt;", value, "createDate");
			return (Criteria) this;
		}
		public Criteria andCreateDateLessThan(Date value) {
			addCriterion("create_date &lt;", value, "createDate");
			return (Criteria) this;
		}
		public Criteria andCreateDateLike(Date value) {
			addCriterion("create_date LIKE", value, "createDate");
			return (Criteria) this;
		}
		public Criteria andCreateDateNotLike(Date value) {
			addCriterion("create_date NOT LIKE", value, "createDate");
			return (Criteria) this;
		}
		public Criteria andCreateDateIn(List<Date> values) {
			addCriterion("create_date IN", values, "createDate");
			return (Criteria) this;
		}
		public Criteria andCreateDateNotIn(List<Date> values) {
			addCriterion("create_date NOT IN", values, "createDate");
			return (Criteria) this;
		}
		public Criteria andCreateDateBetween(Date value1, Date value2) {
			addCriterion("create_date BETWEEN", value1, value2, "createDate");
			return (Criteria) this;
		}
		public Criteria andCreateDateNotBetween(Date value1, Date value2) {
			addCriterion("create_date NOT BETWEEN", value1, value2, "createDate");
			return (Criteria) this;
		}
		public Criteria andUpdateDateIsNull() {
			addCriterion("update_date IS NULL");
			return (Criteria) this;
		}
		public Criteria andUpdateDateIsNotNull() {
			addCriterion("update_date IS NOT NULL");
			return (Criteria) this;
		}
		public Criteria andUpdateDateEqualTo(Date value) {
			addCriterion("update_date =", value, "updateDate");
			return (Criteria) this;
		}
		public Criteria andUpdateDateNotEqualTo(Date value) {
			addCriterion("update_date &lt;&gt;", value, "updateDate");
			return (Criteria) this;
		}
		public Criteria andUpdateDateLessThan(Date value) {
			addCriterion("update_date &lt;", value, "updateDate");
			return (Criteria) this;
		}
		public Criteria andUpdateDateLike(Date value) {
			addCriterion("update_date LIKE", value, "updateDate");
			return (Criteria) this;
		}
		public Criteria andUpdateDateNotLike(Date value) {
			addCriterion("update_date NOT LIKE", value, "updateDate");
			return (Criteria) this;
		}
		public Criteria andUpdateDateIn(List<Date> values) {
			addCriterion("update_date IN", values, "updateDate");
			return (Criteria) this;
		}
		public Criteria andUpdateDateNotIn(List<Date> values) {
			addCriterion("update_date NOT IN", values, "updateDate");
			return (Criteria) this;
		}
		public Criteria andUpdateDateBetween(Date value1, Date value2) {
			addCriterion("update_date BETWEEN", value1, value2, "updateDate");
			return (Criteria) this;
		}
		public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
			addCriterion("update_date NOT BETWEEN", value1, value2, "updateDate");
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
