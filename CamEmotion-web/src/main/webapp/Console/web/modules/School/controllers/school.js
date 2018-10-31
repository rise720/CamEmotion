/**
 * Created by houpp on 2018/2/7.
 */
new Vue(
		{
			el : '#schoolApp',
			data : {
				schoolList : [],
				schoolInfo : {
					schoolname : '',
					// province : '',
					// city : '',
					// district : '',
					// educationlevel : '1',
					// schoolyears : '',
					// classnamedrule : '0',
					// classcount : '',
					// subject : ''
				},
			    btnflag : '',
				error : {
					errorStatus : false,
					errorMsg : ''
				},
				errorText : null
			},
			watch : {
				
			},
			mounted : function() {
				common.checkUrl();
				console.log('------------------/schoolApp');
				initAlert();
				var that = this;
				// that.subjectContrast(that.defaultSubject, that.subject);
				that.QueryList();
				// that.initDistpicker();
			},
			methods : {
				/**
				 * @点击查询按钮
				 */
				QueryList : function() {
					var that = this;
					initAlert();
					that.getSchoolList();
				},

				/**
				 * 初始化课程
				 */
				initDefaultSubject : function() {
					this.defaultSubject = [ {
						name : '语文',
						status : '0'
					}, {
						name : '数学',
						status : '0'
					}, {
						name : '英语',
						status : '0'
					}, {
						name : '历史',
						status : '0'
					}, {
						name : '地理',
						status : '0'
					}, {
						name : '生物',
						status : '0'
					}, {
						name : '物理',
						status : '0'
					}, {
						name : '化学',
						status : '0'
					}, {
						name : '政治',
						status : '0'
					} ];
				},
				/**
				 * 方法说明：添加学科 view
				 * 
				 * @param subjectname
				 */
				addSubject : function(subjectname) {
					if (!subjectname)
						return false;
					var that = this;
					for ( var index in that.subject) {
						if (that.subject[index].name == subjectname) {
							this.error.errorStatus = true;
							this.error.errorMsg = '学科不能重复添加';
							return false;
						}
					}
					that.subject.push({
						name : subjectname
					});
					that.otherSubject = null;
				},
				/**
				 * 方法说明：取消学科 view
				 * 
				 * @param subjectname
				 */
				subSubject : function(subjectname) {
					if (!subjectname)
						return false;
					var that = this;
					if (that.subject.length > 0) {
						that.subject.forEach(function(val, index) {
							if (val.name == subjectname) {
								that.subject.splice(index, 1);
							}
						});
					}
					that.defaultSubject.forEach(function(val) {
						if (val.name == subjectname)
							val.status = '0';
					});
				},

				/**
				 * 方法说明：课程列表 and 默认课程列表 对比
				 * 
				 * @param defaultSubject
				 * @param subject
				 */
				subjectContrast : function(defaultSubject, subject) {
					subject.forEach(function(val) {
						defaultSubject.forEach(function(defval) {
							if (defval.name == val.name)
								defval.status = '1'
						})
					});
				},
				/**
				 * 方法说明：获取学校列表
				 */
				getSchoolList : function() {
					var that = this;
					var params = {
						'schoolname' : ""
					};
					try {
						requsestSer
								.post(camEmotion.findSchoolAll, params)
								.then(
										function(data) {
											if (data.meta.success) {
												that.schoolList = data.data;
											} else {
												bootAlert
														.alertDanger(that
																.fromatterError(data.meta.retCode));
											}
										}, function(response) {
											bootAlert.alertDanger("学校信息请求错误");
											deferred.reject();
										});
					} catch (e) {
						bootAlert.alertDanger(e.message);
					}
				},
				/**
				 * 方法说明：打开信息页(修改)
				 * 
				 * @param obj
				 */
				openUpdateSchoolInfo : function(obj) {
					var that = this;
					initAlert();
					that.initDefaultSubject();
					that.btnflag = 'update';
					that.schoolInfo.id = obj.id;
					that.schoolInfo.schoolname = obj.schoolname;
					// that.schoolInfo.province = obj.province;
					// that.schoolInfo.city = obj.city;
					// that.schoolInfo.district = obj.district;
					// that.schoolInfo.educationlevel = obj.educationlevel;
					// that.schoolInfo.schoolyears = obj.schoolyears;
					// that.schoolInfo.classnamedrule = obj.classnamedrule;
					// that.schoolInfo.classcount = obj.classcount;
					// that.schoolInfo.subject = obj.subject;
					// that.subject = JSON.parse(obj.subject);

					that.error.errorStatus = false;
				},

				/**
				 * 方法说明：打开信息页(添加)
				 * 
				 * @param obj
				 */
				openInsertSchoolInfo : function() {
					var that = this;
					initAlert();
					that.initDefaultSubject();
					that.btnflag = 'insert';
					that.schoolInfo.id = '';
					that.schoolInfo.schoolname = "";
					that.schoolInfo.subject = "";
					that.error.errorStatus = false;
					that.subject = [];
				},
				/**
				 * 方法说明：修改学校信息
				 */
				updateSchoolInfo : function(userInfo) {
					var that = this;
					if (that.schoolInfo.schoolname === undefined
							|| that.schoolInfo.schoolname.replace(
									/(^\s*)|(\s*$)/g, "").length == 0) {
						that.error.errorStatus = true;
						that.error.errorMsg = "报告厅不能为空";
						return false;
					}

					if (that.schoolInfo.schoolname.length > 100) {
						that.error.errorStatus = true;
						that.error.errorMsg = "报告厅名称长度不能超过100";
						return false;
					} else {
						that.error.errorStatus = false;
					}
				

					that.error.errorStatus = false;
					that.schoolInfo.subject = JSON.stringify(that.subject);
					var params = that.schoolInfo;
					try {
						requsestSer
								.post(camEmotion.modifySchoolInfo, params)
								.then(
										function(data) {
											if (data.meta.success) {
												// 关闭窗口
												that.error.errorStatus = false;
												$("#setupModal").modal('hide');
												that.getSchoolList();
												bootAlert.alertSuccess('修改成功');
											} else {
												that.error.errorStatus = true;
												that.error.errorMsg = that
														.fromatterError(data.meta.retCode);
												that.getSchoolList();
											}
										}, function(response) {
											that.error.errorStatus = true;
											that.error.errorMsg = '更新数据错误';
											deferred.reject();
										});
					} catch (e) {
						that.error.errorStatus = true;
						that.error.errorMsg = e.message;
					}
				},
				/**
				 * 方法说明：添加学校信息
				 */
				InsertSchoolInfo : function(e) {
					var that = this;
					if (that.schoolInfo.schoolname === undefined
							|| that.schoolInfo.schoolname.replace(
									/(^\s*)|(\s*$)/g, "").length == 0) {
						that.error.errorStatus = true;
						that.error.errorMsg = "报告厅不能为空";
						return false;
					}
					if (that.schoolInfo.schoolname.length > 100) {
						that.error.errorStatus = true;
						that.error.errorMsg = "报告厅名称长度不能超过100";
						return false;
					}
					// if (that.schoolInfo.schoolyears === undefined
					// 		|| that.schoolInfo.schoolyears == "" 
					// 		|| isNaN(that.schoolInfo.schoolyears) 
					// 		|| that.schoolInfo.schoolyears <= 0) {
					// 	that.error.errorStatus = true;
					// 	that.error.errorMsg = "学年数需为数字且大于0";
					// 	return false;
					// }
					// if (that.schoolInfo.classcount === undefined
					// 		|| that.schoolInfo.classcount == "" 
					// 		|| isNaN(that.schoolInfo.classcount) 
					// 		|| that.schoolInfo.classcount <= 0) {
					// 	that.error.errorStatus = true;
					// 	that.error.errorMsg = "班级数需为数字且大于0";
					// 	return false;
					// }
					// if (!that.subject || this.subject === undefined
					// 		|| that.subject.length <= 0) {
					// 	that.error.errorStatus = true;
					// 	that.error.errorMsg = "学科不能为空";
					// 	return false;
					// }

					that.error.errorStatus = false;
					that.schoolInfo.subject = JSON.stringify(that.subject);
					var params = that.schoolInfo;
					try {
						requsestSer
								.post(camEmotion.saveSchoolInfo, params)
								.then(
										function(data) {
											if (data.meta.success) {
												// 关闭窗口
												that.error.errorStatus = false;
												$("#setupModal").modal('hide');
												that.getSchoolList();
												bootAlert.alertSuccess('添加成功');
											} else {
												that.error.errorStatus = true;
												that.error.errorMsg = that
														.fromatterError(data.meta.retCode);
												that.getSchoolList();
											}
										}, function(response) {
											that.error.errorStatus = true;
											that.error.errorMsg = '添加数据错误';
											deferred.reject();
										});
					} catch (e) {
						that.error.errorStatus = true;
						that.error.errorMsg = e.message;
					}
				},

				/**
				 * 方法说明：删除学校信息
				 */
				delectSchoolInfo : function(obj) {
					initAlert();
					var that = this;
					Modal.alert({
						msg : '内容',
						title : '标题',
						btnok : '确定',
						btncl : '取消'
					});
					Modal
							.confirm({
								msg : "是否删除？"
							})
							.on(
									function(e) {
										if (e) {
											var params = {
												"id" : obj.id
											};
											try {
												requsestSer
														.post(
																camEmotion.deleteSchoolInfo,
																params)
														.then(
																function(data) {
																	if (data.meta.success) {
																		bootAlert
																				.alertSuccess('删除成功');
																		that
																				.getSchoolList();
																	} else {
																		bootAlert
																				.alertDanger(that
																						.fromatterError(data.meta.retCode));
																		that
																				.getSchoolList();
																	}
																},
																function(
																		response) {
																	bootAlert
																			.alertDanger('删除数据错误');
																	deferred
																			.reject();
																});
											} catch (e) {
												bootAlert
														.alertDanger(e.message);
											}
										}
									});
				},
			
				/**
				 * 获取错误信息
				 */
				fromatterError : function(key) {
					var msg = "未知错误";
					switch (key) {
					case '10001':
						msg = "系统异常，请稍候重试";
						break;
					case '60002':
						msg = "该数据已经不存在";
						break;
					default:
						break;
					}
					return msg;
				}

			}
		});