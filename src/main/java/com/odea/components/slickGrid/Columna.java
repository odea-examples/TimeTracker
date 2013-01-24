package com.odea.components.slickGrid;

public class Columna {
String id;
String name;
int width;
int minWidth;
int maxWidth;
String cssClass;
String field;
String formatter;
String editor;
String validator;
public Columna(String id, String name, int width, int minWidth, int maxWidth,
		String cssClass, String field, String formatter, String editor,
		String validator) {
	super();
	this.id = id;
	this.name = name;
	this.width = width;
	this.minWidth = minWidth;
	this.maxWidth = maxWidth;
	this.cssClass = cssClass;
	this.field = field;
	this.formatter = formatter;
	this.editor = editor;
	this.validator = validator;
}
/**
 * @return the id
 */
public String getId() {
	return id;
}
/**
 * @param id the id to set
 */
public void setId(String id) {
	this.id = id;
}
/**
 * @return the name
 */
public String getName() {
	return name;
}
/**
 * @param name the name to set
 */
public void setName(String name) {
	this.name = name;
}
/**
 * @return the width
 */
public int getWidth() {
	return width;
}
/**
 * @param width the width to set
 */
public void setWidth(int width) {
	this.width = width;
}
/**
 * @return the minWidth
 */
public int getMinWidth() {
	return minWidth;
}
/**
 * @param minWidth the minWidth to set
 */
public void setMinWidth(int minWidth) {
	this.minWidth = minWidth;
}
/**
 * @return the maxWidth
 */
public int getMaxWidth() {
	return maxWidth;
}
/**
 * @param maxWidth the maxWidth to set
 */
public void setMaxWidth(int maxWidth) {
	this.maxWidth = maxWidth;
}
/**
 * @return the cssClass
 */
public String getCssClass() {
	return cssClass;
}
/**
 * @param cssClass the cssClass to set
 */
public void setCssClass(String cssClass) {
	this.cssClass = cssClass;
}
/**
 * @return the field
 */
public String getField() {
	return field;
}
/**
 * @param field the field to set
 */
public void setField(String field) {
	this.field = field;
}
/**
 * @return the formatter
 */
public String getFormatter() {
	return formatter;
}
/**
 * @param formatter the formatter to set
 */
public void setFormatter(String formatter) {
	this.formatter = formatter;
}
/**
 * @return the editor
 */
public String getEditor() {
	return editor;
}
/**
 * @param editor the editor to set
 */
public void setEditor(String editor) {
	this.editor = editor;
}
/**
 * @return the validator
 */
public String getValidator() {
	return validator;
}
/**
 * @param validator the validator to set
 */
public void setValidator(String validator) {
	this.validator = validator;
}
}
