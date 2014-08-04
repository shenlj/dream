package com.wholetech.commons.util;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wholetech.commons.query.QueryParam;
import com.wholetech.commons.query.QueryParam.MatchType;

/**
 * ===============================================================================
 * Copyright (c) 2007-2008 Digitalchina CO.,LTD. All rights reserved.
 */
public class CriterionUtil {

	protected Logger logger = LoggerFactory.getLogger(CriterionUtil.class);

	public Criterion generateCriteria(final Object criteria, final String key, final Object tempStr, final int type,
			final boolean flag, final MatchType method) {

		// “+”、“-”体现大小关系并将此值项拆分为逻辑与的两项。
		Criterion criterion = null;
		if (tempStr instanceof String) {
			String tempString = ((String) tempStr).trim();
			tempString = tempString.replaceAll("　", " ");
			tempString = tempString.replaceAll("＝", "=");
			tempString = tempString.replaceAll("＋", "+");
			tempString = tempString.replaceAll("－", "-");
			tempString = tempString.replaceAll("—", "-");

			int indexFlag = tempString.indexOf(" ");
			this.logger.debug("空格的位置，决定是否有多值情况:", indexFlag);
			int count = 0;// 如果是最前面的则需要有“+”“-”符号开头，否则可以不要

			while (indexFlag >= 0 || count == 0) {
				this.logger.debug("循环的次数:", count);
				String frontStr = null;
				if (indexFlag > 0 || indexFlag == 0 && tempString.trim().indexOf(" ") > 0) {
					if (indexFlag == 0) {
						indexFlag = tempString.trim().indexOf(" ");
					}
					frontStr = tempString.trim().substring(0, indexFlag).trim();
				} else {
					frontStr = tempString.trim();
				}
				this.logger.debug("前面的字符串值:", frontStr);
				if (type == 0) {// 0代表字符型
					if (indexFlag >= 0) {
						// 空格数大于0，代表有多值，多值情况使用or的关系
						final Criterion criterionTemp = createStringCriterion(key, frontStr, false, flag);
						if (count == 0) {
							criterion = criterionTemp;
						} else {
							criterion = Restrictions.or(criterion, criterionTemp);
						}
					} else {
						criterion = createStringCriterion(key, frontStr, false, flag);
					}
				} else if (type == 1) {// 1代表数字型
					if (indexFlag >= 0) {
						final Criterion criterionTemp = createNumCriterion(key, count, frontStr, method);
						if (count == 0) {
							criterion = criterionTemp;
						} else {
							criterion = Restrictions.or(criterion, criterionTemp);
						}
					} else {
						criterion = createNumCriterion(key, count, frontStr, method);
					}

				} else if (type == 8 || type == 12) {// 8,12代表日期型
					// if (DateUtil.checkIsDate(frontStr)) {
					if (indexFlag >= 0) {// 多值情况
						final Criterion criterionTemp = createDateCriterion(key,
								frontStr, count, type, flag);
						if (count == 0) {
							criterion = criterionTemp;
						} else {
							criterion = Restrictions.or(criterion, criterionTemp);
						}
					} else {
						criterion = createDateCriterion(key, frontStr, count, type, flag);
					}
				} else if (type == 10) {// 10代表大字符串
					if (indexFlag >= 0) {
						final Criterion criterionTemp = createBigStringCriterion(key, frontStr, flag);
						if (count == 0) {
							criterion = criterionTemp;
						} else {
							criterion = Restrictions.or(criterion, criterionTemp);
						}
					} else {
						criterion = createBigStringCriterion(key, frontStr, flag);
					}

				}
				this.logger.debug("组合的查询条件:", criterion);
				count++;
				if (indexFlag > -1) {
					tempString = tempString.trim().substring(indexFlag);
					indexFlag = tempString.indexOf(" ");
				}
			}
		} else {
			criterion = Restrictions.eq(key, tempStr);
		}
		// if (criterion != null) {
		// if (criteria instanceof Criteria) {
		// ((Criteria) criteria).add(criterion);
		// } else if (criteria instanceof Criterion) {
		// ((Conjunction) criteria).add(criterion);
		// }
		// }
		this.logger.debug("组合后的查询条件:", criteria);
		return criterion;
	}

	/**
	 * @version 1.0 功能描述：
	 * @see
	 * @param flag标志为是否按默认的字符串匹配后缀模式
	 * @return
	 * @exception BaseBusinessException
	 */
	protected Criterion createStringCriterion(final String key, final String frontStr, final boolean flag,
			final boolean isCriteria) {

		Criterion criterionTemp = null;
		if (frontStr.startsWith("%") || frontStr.endsWith("%")) {
			if (!isCriteria) {
				criterionTemp = Restrictions.like(key, "'" + frontStr + "'");
			} else {
				criterionTemp = Restrictions.like(key, frontStr);
			}
		} else if (flag) {
			if (!isCriteria) {
				criterionTemp = Restrictions.like(key, "'" + frontStr + "%'");
			} else {
				criterionTemp = Restrictions.like(key, frontStr + "%");
			}
		} else {
			if (!isCriteria) {
				criterionTemp = Restrictions.eq(key, "'" + frontStr + "'");
			} else {
				criterionTemp = Restrictions.eq(key, frontStr);
			}
		}
		return criterionTemp;
	}

	/**
	 * @version 1.0 功能描述：
	 * @see
	 * @param flag标志为是否按默认的字符串匹配后缀模式
	 * @return
	 * @exception BaseBusinessException
	 */
	protected Criterion createDateCriterion(final String key, String frontStr, final int count, final int type,
			final boolean isCriteria) {

		Criterion criterionTemp = null;
		String suff = null;
		String pref = null;
		if (type == 12) {
			suff = "01010000";// 月日包含小时分最小值
			pref = "12312359"; // 月日小时分最大值
		} else if (type == 8) {
			suff = "0101";// 月日最小值
			pref = "1231"; // 月日最大值
		}
		if (frontStr.startsWith("+") || count > 0) {// 有+号开头的
			if (count == 0) {
				frontStr = frontStr.substring(1);
			}
			if (frontStr.indexOf("-") >= 0) {// 有+号开头并有中间有-号
				final String tempFrontStr = frontStr.substring(0, frontStr.indexOf("-"));
				frontStr = frontStr.substring(frontStr.indexOf("-") + 1);
				final int lengthFront = tempFrontStr.length();
				final int lengthEnd = frontStr.length();
				if (!tempFrontStr.equals("") && !frontStr.equals("")) {
					if (!isCriteria) {
						StringBuffer bufSql = new StringBuffer();
						StringBuffer bufSql2 = new StringBuffer();
						bufSql.append("'").append(tempFrontStr).append(
								suff.substring(8 - (12 - lengthFront))).append("'");
						bufSql2.append("'").append(frontStr).append(
								pref.substring(8 - (12 - lengthEnd))).append("'");
						criterionTemp = Restrictions.and(Restrictions.ge(key,
								bufSql.toString()), Restrictions.le(key, bufSql2.toString()));
						bufSql = null;
						bufSql2 = null;

					} else {
						criterionTemp = Restrictions.and(
								Restrictions.ge(key, tempFrontStr + suff.substring(8 - (12 - lengthFront))),
								Restrictions.le(key, frontStr + pref.substring(8 - (12 - lengthEnd))));
					}

				} else if (tempFrontStr.equals("") && !frontStr.equals("")) {// 前面部分为空

					if (!isCriteria) {
						StringBuffer bufSql = new StringBuffer();
						bufSql.append("'").append(frontStr).append(
								pref.substring(8 - (12 - lengthEnd))).append("'");
						criterionTemp = Restrictions.le(key, bufSql.toString());
						bufSql = null;

					} else {
						criterionTemp = Restrictions.le(key, frontStr + pref.substring(8 - (12 - lengthEnd)));
					}

				} else if (!tempFrontStr.equals("") && frontStr.equals("")) {// 后面部分为空

					if (!isCriteria) {
						StringBuffer bufSql = new StringBuffer();
						bufSql.append("'").append(tempFrontStr).append(
								suff.substring(8 - (12 - lengthFront))).append("'");
						criterionTemp = Restrictions.ge(key, bufSql.toString());
						bufSql = null;
					} else {
						criterionTemp = Restrictions.ge(key, tempFrontStr + suff.substring(8 - (12 - lengthFront)));
					}
				}

			} else {// 只有+号开头的（这样肯定是一个值的情况），大于当前时间的所有日期
				if (frontStr.indexOf("+") >= 0) {
					frontStr = frontStr.substring(frontStr.indexOf("+") + 1);
				} else if (frontStr.indexOf("-") >= 0) {
					frontStr = frontStr.substring(frontStr.indexOf("-") + 1);
				}
				final int length = frontStr.length();

				if (!isCriteria) {
					StringBuffer bufSql = new StringBuffer();
					bufSql.append("'").append(frontStr).append(
							suff.substring(8 - (12 - length))).append("'");
					criterionTemp = Restrictions.ge(key, bufSql.toString());
					bufSql = null;
				} else {
					criterionTemp = Restrictions.ge(key, frontStr
							+ suff.substring(8 - (12 - length)));

				}
			}

		} else if (frontStr.startsWith("-") || count > 0) {// 有-号开头
			if (count == 0) {
				frontStr = frontStr.substring(1);
			}
			if (frontStr.indexOf("+") >= 0) {// 有-号开头并且中间有+号
				final String tempFrontStr = frontStr.substring(0, frontStr
						.indexOf("+"));
				frontStr = frontStr.substring(frontStr.indexOf("+") + 1);
				final int lengthFront = tempFrontStr.length();
				final int lengthEnd = frontStr.length();

				if (!tempFrontStr.equals("") && !frontStr.equals("")) {

					if (!isCriteria) {
						StringBuffer bufSql = new StringBuffer();
						StringBuffer bufSql2 = new StringBuffer();
						bufSql.append("'").append(tempFrontStr).append(
								suff.substring(8 - (12 - lengthFront))).append(
										"'");
						bufSql2.append("'").append(frontStr).append(
								pref.substring(8 - (12 - lengthEnd))).append(
										"'");
						criterionTemp = Restrictions.and(Restrictions.le(key,
								bufSql.toString()), Restrictions.ge(key, bufSql2
										.toString()));
						bufSql = null;
						bufSql2 = null;

					} else {
						criterionTemp = Restrictions
								.and(
										Restrictions
										.le(
												key,
												tempFrontStr
												+ suff
												.substring(8 - (12 - lengthFront))),
												Restrictions
												.ge(
														key,
														frontStr
														+ pref
														.substring(8 - (12 - lengthEnd))));

					}

					// criterionTemp = Expression.sql(bufSql.toString());
				} else if (tempFrontStr.equals("") && !frontStr.equals("")) {// 前面部分为空

					if (!isCriteria) {
						StringBuffer bufSql = new StringBuffer();
						bufSql.append("'").append(frontStr).append(
								pref.substring(8 - (12 - lengthEnd))).append(
										"'");
						criterionTemp = Restrictions.ge(key, bufSql.toString());
						bufSql = null;

					} else {
						criterionTemp = Restrictions.ge(key, frontStr
								+ pref.substring(8 - (12 - lengthEnd)));

					}
				} else if (!tempFrontStr.equals("") && frontStr.equals("")) {// 后面部分为空

					if (!isCriteria) {
						StringBuffer bufSql = new StringBuffer();
						bufSql.append("'").append(tempFrontStr).append(
								suff.substring(8 - (12 - lengthFront))).append(
										"'");
						criterionTemp = Restrictions.le(key, bufSql.toString());
						bufSql = null;

					} else {
						criterionTemp = Restrictions.le(key, tempFrontStr
								+ suff.substring(8 - (12 - lengthFront)));

					}

				}
			} else {// 只有-号开头（这样肯定是一个值的情况），小于当前时间的所有日期
				if (frontStr.indexOf("+") >= 0) {
					frontStr = frontStr.substring(frontStr.indexOf("+") + 1);
				} else if (frontStr.indexOf("-") >= 0) {
					frontStr = frontStr.substring(frontStr.indexOf("-") + 1);
				}
				final int length = frontStr.length();

				if (!isCriteria) {
					final StringBuffer bufSql = new StringBuffer();
					bufSql.append("'").append(frontStr).append(
							pref.substring(8 - (12 - length))).append("'");
					criterionTemp = Restrictions.le(key, bufSql.toString());

				} else {
					criterionTemp = Restrictions.le(key, frontStr
							+ pref.substring(8 - (12 - length)));

				}
			}

		} else {// 没有+号或者-号开头的情况
			if (frontStr.indexOf("-") >= 0) {// 有-号的情况下，条件都是在当年、当月、或者当日、当时以内
				final String tempFrontStr = frontStr.substring(0, frontStr
						.indexOf("-"));
				frontStr = frontStr.substring(frontStr.indexOf("-") + 1);
				final int lengthFront = tempFrontStr.length();
				final int lengthEnd = frontStr.length();

				if (!isCriteria) {
					StringBuffer bufSql = new StringBuffer();
					StringBuffer bufSql2 = new StringBuffer();
					bufSql.append("'").append(tempFrontStr).append(
							suff.substring(8 - (12 - lengthFront))).append("'");
					bufSql2.append("'").append(frontStr).append(
							pref.substring(8 - (12 - lengthEnd))).append("'");

					criterionTemp = Restrictions.and(Restrictions.ge(key, bufSql
							.toString()), Restrictions
							.le(key, bufSql2.toString()));
					bufSql = null;
					bufSql2 = null;
				} else {
					criterionTemp = Restrictions.and(Restrictions.ge(key,
							tempFrontStr
							+ suff.substring(8 - (12 - lengthFront))),
							Restrictions.le(key, frontStr
									+ pref.substring(8 - (12 - lengthEnd))));
				}
			} else {// 单个值并且没有+或者-号的情况下，条件都是在当年、当月、或者当日、当时、当分以内
				frontStr.length();
				if (!isCriteria) {
					criterionTemp = Restrictions.like(key, "'" + frontStr + "%'");
				} else {
					criterionTemp = Restrictions.like(key, frontStr + "%");
				}
			}
		}
		return criterionTemp;
	}

	protected Criterion createBigStringCriterion(final String key, final String frontStr,
			final boolean isCriteria) {

		Criterion criterionTemp = null;
		if (frontStr.startsWith("=") || frontStr.endsWith("=")) {
			if (frontStr.startsWith("=")) {
				final String tempStr = frontStr.substring(1);
				if (!isCriteria) {
					criterionTemp = Restrictions.eq(key, "'" + tempStr
							+ "'");
				} else {
					criterionTemp = Restrictions.eq(key, tempStr);
				}
			} else {
				final String tempStr = frontStr.substring(0, frontStr.length() - 1);
				if (!isCriteria) {
					criterionTemp = Restrictions.eq(key, "'" + tempStr
							+ "'");
				} else {
					criterionTemp = Restrictions.eq(key, tempStr);
				}
			}

		} else {
			if (!isCriteria) {
				criterionTemp = Restrictions.like(key, "'%" + frontStr
						+ "%'");
			} else {
				criterionTemp = Restrictions.like(key, "%" + frontStr
						+ "%");
			}

		}
		return criterionTemp;
	}

	protected Criterion createNumCriterion(final String key, final int count, String frontStr, final MatchType method) {

		Criterion criterionTemp = null;
		if (frontStr.startsWith("+") || count > 0) {// 以+号开头
			if (count == 0) {
				frontStr = frontStr.substring(1);
			}
			if (frontStr.indexOf("-") >= 0) {// 以加号开头并且有减号
				final String tempFrontStr = frontStr.substring(0, frontStr
						.indexOf("-"));
				frontStr = frontStr.substring(frontStr.indexOf("-") + 1);
				if (!tempFrontStr.equals("") && !frontStr.equals("")) {
					// 数字型
					if (QueryParam.MatchType.LONG.equals(method)) {
						criterionTemp = Restrictions.and(Restrictions.ge(key,
								Long.valueOf(tempFrontStr)), Restrictions.le(key, Long.valueOf(frontStr)));
					} else if (QueryParam.MatchType.INTEGER.equals(method)) {
						criterionTemp = Restrictions.and(Restrictions.ge(key,
								Integer.valueOf(tempFrontStr)), Restrictions.le(key, Integer.valueOf(frontStr)));
					} else if (QueryParam.MatchType.DOUBLE.equals(method)) {
						criterionTemp = Restrictions.and(Restrictions.ge(key,
								Double.valueOf(tempFrontStr)), Restrictions.le(key, Double.valueOf(frontStr)));
					} else if (QueryParam.MatchType.FLOAT.equals(method)) {
						criterionTemp = Restrictions.and(Restrictions.ge(key,
								Float.valueOf(tempFrontStr)), Restrictions.le(key, Float.valueOf(frontStr)));
					}
				} else if (tempFrontStr.equals("") && !frontStr.equals("")) {// 前面部分为空
					if (QueryParam.MatchType.LONG.equals(method)) {
						criterionTemp = Restrictions.le(key, Long.valueOf(frontStr));
					} else if (QueryParam.MatchType.INTEGER.equals(method)) {
						criterionTemp = Restrictions.le(key, Integer.valueOf(frontStr));
					} else if (QueryParam.MatchType.DOUBLE.equals(method)) {
						criterionTemp = Restrictions.le(key, Double.valueOf(frontStr));
					} else if (QueryParam.MatchType.FLOAT.equals(method)) {
						criterionTemp = Restrictions.le(key, Float.valueOf(frontStr));
					}

				} else if (!tempFrontStr.equals("") && frontStr.equals("")) {// 后面部分为空
					if (QueryParam.MatchType.LONG.equals(method)) {
						criterionTemp = Restrictions.ge(key, Long.valueOf(tempFrontStr));
					} else if (QueryParam.MatchType.INTEGER.equals(method)) {
						criterionTemp = Restrictions.ge(key, Integer.valueOf(tempFrontStr));
					} else if (QueryParam.MatchType.DOUBLE.equals(method)) {
						criterionTemp = Restrictions.ge(key, Double.valueOf(tempFrontStr));
					} else if (QueryParam.MatchType.FLOAT.equals(method)) {
						criterionTemp = Restrictions.ge(key, Float.valueOf(tempFrontStr));
					}
					// criterionTemp = Expression.ge(key, tempFrontStr);
				}
			} else {
				if (QueryParam.MatchType.LONG.equals(method)) {
					criterionTemp = Restrictions.ge(key, Long.valueOf(frontStr));
				} else if (QueryParam.MatchType.INTEGER.equals(method)) {
					criterionTemp = Restrictions.ge(key, Integer.valueOf(frontStr));
				} else if (QueryParam.MatchType.DOUBLE.equals(method)) {
					criterionTemp = Restrictions.ge(key, Double.valueOf(frontStr));
				} else if (QueryParam.MatchType.FLOAT.equals(method)) {
					criterionTemp = Restrictions.ge(key, Float.valueOf(frontStr));
				}
				// criterionTemp = Expression.ge(key, frontStr);
			}
		} else if (frontStr.startsWith("-") || count > 0) {
			if (count == 0) {
				frontStr = frontStr.substring(1);
			}
			if (frontStr.indexOf("+") >= 0) {
				final String tempFrontStr = frontStr.substring(0, frontStr
						.indexOf("+"));
				frontStr = frontStr.substring(frontStr.indexOf("+") + 1);
				if (!tempFrontStr.equals("") && !frontStr.equals("")) {
					if (QueryParam.MatchType.LONG.equals(method)) {
						criterionTemp = Restrictions.and(Restrictions.le(key,
								Long.valueOf(tempFrontStr)), Restrictions.ge(key, Long.valueOf(frontStr)));
					} else if (QueryParam.MatchType.INTEGER.equals(method)) {
						criterionTemp = Restrictions.and(Restrictions.le(key,
								Integer.valueOf(tempFrontStr)), Restrictions.ge(key, Integer.valueOf(frontStr)));
					} else if (QueryParam.MatchType.DOUBLE.equals(method)) {
						criterionTemp = Restrictions.and(Restrictions.le(key,
								Double.valueOf(tempFrontStr)), Restrictions.ge(key, Double.valueOf(frontStr)));
					} else if (QueryParam.MatchType.FLOAT.equals(method)) {
						criterionTemp = Restrictions.and(Restrictions.le(key,
								Float.valueOf(tempFrontStr)), Restrictions.ge(key, Float.valueOf(frontStr)));
					}
					// criterionTemp = Expression.and(Expression.le(key,
					// tempFrontStr), Expression.ge(key, frontStr));
				} else if (tempFrontStr.equals("") && !frontStr.equals("")) {// 前面部分为空
					if (QueryParam.MatchType.LONG.equals(method)) {
						criterionTemp = Restrictions.ge(key, Long.valueOf(frontStr));
					} else if (QueryParam.MatchType.INTEGER.equals(method)) {
						criterionTemp = Restrictions.ge(key, Integer.valueOf(frontStr));
					} else if (QueryParam.MatchType.DOUBLE.equals(method)) {
						criterionTemp = Restrictions.ge(key, Double.valueOf(frontStr));
					} else if (QueryParam.MatchType.FLOAT.equals(method)) {
						criterionTemp = Restrictions.ge(key, Float.valueOf(frontStr));
					}
					// criterionTemp = Expression.ge(key, frontStr);
				} else if (!tempFrontStr.equals("") && frontStr.equals("")) {// 后面部分为空
					if (QueryParam.MatchType.LONG.equals(method)) {
						criterionTemp = Restrictions.le(key, Long.valueOf(tempFrontStr));
					} else if (QueryParam.MatchType.INTEGER.equals(method)) {
						criterionTemp = Restrictions.le(key, Integer.valueOf(tempFrontStr));
					} else if (QueryParam.MatchType.DOUBLE.equals(method)) {
						criterionTemp = Restrictions.le(key, Double.valueOf(tempFrontStr));
					} else if (QueryParam.MatchType.FLOAT.equals(method)) {
						criterionTemp = Restrictions.le(key, Float.valueOf(tempFrontStr));
					}
					// criterionTemp = Expression.le(key, tempFrontStr);
				}
			} else {
				if (QueryParam.MatchType.LONG.equals(method)) {
					criterionTemp = Restrictions.le(key, Long.valueOf(frontStr));
				} else if (QueryParam.MatchType.INTEGER.equals(method)) {
					criterionTemp = Restrictions.le(key, Integer.valueOf(frontStr));
				} else if (QueryParam.MatchType.DOUBLE.equals(method)) {
					criterionTemp = Restrictions.le(key, Double.valueOf(frontStr));
				} else if (QueryParam.MatchType.FLOAT.equals(method)) {
					criterionTemp = Restrictions.le(key, Float.valueOf(frontStr));
				}
				// criterionTemp = Expression.le(key, frontStr);
			}
		} else {
			if (frontStr.indexOf("-") >= 0) {// 有-号的情况下，条件都是在当年、当月、或者当日、当时以内
				final String tempFrontStr = frontStr.substring(0, frontStr
						.indexOf("-"));
				frontStr = frontStr.substring(frontStr.indexOf("-") + 1);
				// int lengthFront = tempFrontStr.length();
				// int lengthEnd = frontStr.length();

				if (QueryParam.MatchType.LONG.equals(method)) {
					criterionTemp = Restrictions.and(Restrictions.ge(key, Long.valueOf(tempFrontStr)),
							Restrictions.le(key, Long.valueOf(frontStr)));
				} else if (QueryParam.MatchType.INTEGER.equals(method)) {
					criterionTemp = Restrictions.and(Restrictions.ge(key, Integer.valueOf(tempFrontStr)),
							Restrictions.le(key, Integer.valueOf(frontStr)));
				} else if (QueryParam.MatchType.DOUBLE.equals(method)) {
					criterionTemp = Restrictions.and(Restrictions.ge(key, Double.valueOf(tempFrontStr)),
							Restrictions.le(key, Double.valueOf(frontStr)));
				} else if (QueryParam.MatchType.FLOAT.equals(method)) {
					criterionTemp = Restrictions.and(Restrictions.ge(key, Float.valueOf(tempFrontStr)),
							Restrictions.le(key, Float.valueOf(frontStr)));
				}
			} else {
				if (QueryParam.MatchType.LONG.equals(method)) {
					criterionTemp = Restrictions.eq(key, Long.valueOf(frontStr));
				} else if (QueryParam.MatchType.INTEGER.equals(method)) {
					criterionTemp = Restrictions.eq(key, Integer.valueOf(frontStr));
				} else if (QueryParam.MatchType.DOUBLE.equals(method)) {
					criterionTemp = Restrictions.eq(key, Double.valueOf(frontStr));
				} else if (QueryParam.MatchType.FLOAT.equals(method)) {
					criterionTemp = Restrictions.eq(key, Float.valueOf(frontStr));
				}
			}
			// criterionTemp = Expression.eq(key, frontStr);
		}
		return criterionTemp;
	}
}
