// SQL_db: cictems
// SQL_table: student
// Mono Models
// Monosync Framewrok v1.8.x
// Created: Nov 18, 2017 02:34:41 PM
// Generated using LazyMono
// This code is computer generated, do not modify
// Author: Jhon Melvin Nieto Perello
// Contact: jhmvinperello@gmail.com
//
// The Framework uses Hibernate as its ORM
// For more information about Hibernate visit hibernate.org

package app.lazy.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "student", catalog = "cictems")
public class StudentMapping implements java.io.Serializable {


private java.lang.Integer cict_id;
private java.lang.String id;
private java.lang.Integer CURRICULUM_id;
private java.util.Date curriculum_assignment;
private java.lang.Integer PREP_id;
private java.util.Date prep_assignment;
private java.lang.String last_name;
private java.lang.String first_name;
private java.lang.String middle_name;
private java.lang.String gender;
private java.lang.Integer year_level;
private java.lang.String section;
private java.lang.Integer _group;
private java.lang.Integer has_profile;
private java.lang.String enrollment_type;
private java.lang.String admission_year;
private java.lang.String college;
private java.lang.String campus;
private java.lang.String residency;
private java.lang.String class_type;
private java.lang.String university;
private java.lang.String created_by;
private java.util.Date created_date;
private java.lang.String updated_by;
private java.util.Date updated_date;
private java.lang.Integer verified;
private java.util.Date verification_date;
private java.lang.Integer verfied_by;
private java.lang.Integer last_evaluation_term;
private java.lang.Integer active;

@Id
@GeneratedValue(strategy = IDENTITY)
@Column(name = "cict_id", nullable = false, length = 10)
public java.lang.Integer getCict_id() {
	return this.cict_id;
}

public void setCict_id(java.lang.Integer fieldCictId) {
	this.cict_id = fieldCictId;
}

@Column(name = "id", nullable = true, length = 50)
public java.lang.String getId() {
	return this.id;
}

public void setId(java.lang.String fieldId) {
	this.id = fieldId;
}

@Column(name = "CURRICULUM_id", nullable = true, length = 10)
public java.lang.Integer getCURRICULUM_id() {
	return this.CURRICULUM_id;
}

public void setCURRICULUM_id(java.lang.Integer fieldCurriculumId) {
	this.CURRICULUM_id = fieldCurriculumId;
}

@Column(name = "curriculum_assignment", nullable = true, length = 19)
@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
public java.util.Date getCurriculum_assignment() {
	return this.curriculum_assignment;
}

public void setCurriculum_assignment(java.util.Date fieldCurriculumAssignment) {
	this.curriculum_assignment = fieldCurriculumAssignment;
}

@Column(name = "PREP_id", nullable = true, length = 10)
public java.lang.Integer getPREP_id() {
	return this.PREP_id;
}

public void setPREP_id(java.lang.Integer fieldPrepId) {
	this.PREP_id = fieldPrepId;
}

@Column(name = "prep_assignment", nullable = true, length = 19)
@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
public java.util.Date getPrep_assignment() {
	return this.prep_assignment;
}

public void setPrep_assignment(java.util.Date fieldPrepAssignment) {
	this.prep_assignment = fieldPrepAssignment;
}

@Column(name = "last_name", nullable = true, length = 100)
public java.lang.String getLast_name() {
	return this.last_name;
}

public void setLast_name(java.lang.String fieldLastName) {
	this.last_name = fieldLastName;
}

@Column(name = "first_name", nullable = true, length = 100)
public java.lang.String getFirst_name() {
	return this.first_name;
}

public void setFirst_name(java.lang.String fieldFirstName) {
	this.first_name = fieldFirstName;
}

@Column(name = "middle_name", nullable = true, length = 100)
public java.lang.String getMiddle_name() {
	return this.middle_name;
}

public void setMiddle_name(java.lang.String fieldMiddleName) {
	this.middle_name = fieldMiddleName;
}

@Column(name = "gender", nullable = true, length = 100)
public java.lang.String getGender() {
	return this.gender;
}

public void setGender(java.lang.String fieldGender) {
	this.gender = fieldGender;
}

@Column(name = "year_level", nullable = true, length = 10)
public java.lang.Integer getYear_level() {
	return this.year_level;
}

public void setYear_level(java.lang.Integer fieldYearLevel) {
	this.year_level = fieldYearLevel;
}

@Column(name = "section", nullable = true, length = 100)
public java.lang.String getSection() {
	return this.section;
}

public void setSection(java.lang.String fieldSection) {
	this.section = fieldSection;
}

@Column(name = "_group", nullable = true, length = 10)
public java.lang.Integer get_group() {
	return this._group;
}

public void set_group(java.lang.Integer fieldGroup) {
	this._group = fieldGroup;
}

@Column(name = "has_profile", nullable = true, length = 10)
public java.lang.Integer getHas_profile() {
	return this.has_profile;
}

public void setHas_profile(java.lang.Integer fieldHasProfile) {
	this.has_profile = fieldHasProfile;
}

@Column(name = "enrollment_type", nullable = true, length = 50)
public java.lang.String getEnrollment_type() {
	return this.enrollment_type;
}

public void setEnrollment_type(java.lang.String fieldEnrollmentType) {
	this.enrollment_type = fieldEnrollmentType;
}

@Column(name = "admission_year", nullable = true, length = 50)
public java.lang.String getAdmission_year() {
	return this.admission_year;
}

public void setAdmission_year(java.lang.String fieldAdmissionYear) {
	this.admission_year = fieldAdmissionYear;
}

@Column(name = "college", nullable = true, length = 50)
public java.lang.String getCollege() {
	return this.college;
}

public void setCollege(java.lang.String fieldCollege) {
	this.college = fieldCollege;
}

@Column(name = "campus", nullable = true, length = 50)
public java.lang.String getCampus() {
	return this.campus;
}

public void setCampus(java.lang.String fieldCampus) {
	this.campus = fieldCampus;
}

@Column(name = "residency", nullable = true, length = 50)
public java.lang.String getResidency() {
	return this.residency;
}

public void setResidency(java.lang.String fieldResidency) {
	this.residency = fieldResidency;
}

@Column(name = "class_type", nullable = true, length = 50)
public java.lang.String getClass_type() {
	return this.class_type;
}

public void setClass_type(java.lang.String fieldClassType) {
	this.class_type = fieldClassType;
}

@Column(name = "university", nullable = true, length = 50)
public java.lang.String getUniversity() {
	return this.university;
}

public void setUniversity(java.lang.String fieldUniversity) {
	this.university = fieldUniversity;
}

@Column(name = "created_by", nullable = true, length = 100)
public java.lang.String getCreated_by() {
	return this.created_by;
}

public void setCreated_by(java.lang.String fieldCreatedBy) {
	this.created_by = fieldCreatedBy;
}

@Column(name = "created_date", nullable = true, length = 19)
@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
public java.util.Date getCreated_date() {
	return this.created_date;
}

public void setCreated_date(java.util.Date fieldCreatedDate) {
	this.created_date = fieldCreatedDate;
}

@Column(name = "updated_by", nullable = true, length = 100)
public java.lang.String getUpdated_by() {
	return this.updated_by;
}

public void setUpdated_by(java.lang.String fieldUpdatedBy) {
	this.updated_by = fieldUpdatedBy;
}

@Column(name = "updated_date", nullable = true, length = 19)
@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
public java.util.Date getUpdated_date() {
	return this.updated_date;
}

public void setUpdated_date(java.util.Date fieldUpdatedDate) {
	this.updated_date = fieldUpdatedDate;
}

@Column(name = "verified", nullable = true, length = 10)
public java.lang.Integer getVerified() {
	return this.verified;
}

public void setVerified(java.lang.Integer fieldVerified) {
	this.verified = fieldVerified;
}

@Column(name = "verification_date", nullable = true, length = 19)
@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
public java.util.Date getVerification_date() {
	return this.verification_date;
}

public void setVerification_date(java.util.Date fieldVerificationDate) {
	this.verification_date = fieldVerificationDate;
}

@Column(name = "verfied_by", nullable = true, length = 10)
public java.lang.Integer getVerfied_by() {
	return this.verfied_by;
}

public void setVerfied_by(java.lang.Integer fieldVerfiedBy) {
	this.verfied_by = fieldVerfiedBy;
}

@Column(name = "last_evaluation_term", nullable = true, length = 10)
public java.lang.Integer getLast_evaluation_term() {
	return this.last_evaluation_term;
}

public void setLast_evaluation_term(java.lang.Integer fieldLastEvaluationTerm) {
	this.last_evaluation_term = fieldLastEvaluationTerm;
}

@Column(name = "active", nullable = true, length = 10)
public java.lang.Integer getActive() {
	return this.active;
}

public void setActive(java.lang.Integer fieldActive) {
	this.active = fieldActive;
}

}
