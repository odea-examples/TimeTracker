package com.odea;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.PatternValidator;
import org.joda.time.LocalDate;

import com.odea.behavior.numberComma.NumberCommaBehavior;
import com.odea.behavior.onlyNumber.OnlyNumberBehavior;
import com.odea.components.ajax.AbstractInitializableComponentBehavior;
import com.odea.components.datepicker.DatePickerDTO;
import com.odea.components.datepicker.HorasCargadasPorDia;
import com.odea.components.slickGrid.Columna;
import com.odea.components.slickGrid.Data;
import com.odea.components.slickGrid.SlickGrid;
import com.odea.components.yuidatepicker.YuiDatePicker;
import com.odea.domain.Actividad;
import com.odea.domain.Entrada;
import com.odea.domain.Proyecto;
import com.odea.domain.Usuario;
import com.odea.services.DAOService;
import com.odea.validators.duracion.DurationValidator;
import com.odea.validators.ticketExterno.OnRelatedFieldsNullValidator;

public class EntradasPage extends BasePage {

	private static final long serialVersionUID = 1088210443697851501L;

	@SpringBean
	private transient DAOService daoService;

	private Usuario usuario;

//	IModel<List<Entrada>> lstEntradasModel;
	IModel<String> lstDataModel;
	IModel<Integer> horasSemanalesModel;
	IModel<Integer> horasMesModel;
	IModel<Integer> horasDiaModel;
	IModel<String> slickGridJsonCols;
	RadioChoice<String> selectorTiempo;
	Label mensajeProyecto;
	Label mensajeActividad;
	LocalDate fechaActual = new LocalDate();
	Label horasAcumuladasDia;
	Label horasAcumuladasSemana;
	Label horasAcumuladasMes;

	EntradaForm form;

	WebMarkupContainer listViewContainer;
	WebMarkupContainer radioContainer;
	WebMarkupContainer labelContainer;

	public EntradasPage() {
		final Subject subject = SecurityUtils.getSubject();

		this.slickGridJsonCols = Model.of(this.getColumns());

		this.usuario = this.daoService.getUsuario(subject.getPrincipal().toString());

		this.lstDataModel = new LoadableDetachableModel<String>() {
			@Override
			protected String load() {
				return daoService.toJson(daoService.getEntradasDia(EntradasPage.this.usuario,EntradasPage.this.fechaActual));
			}
		};

		this.horasSemanalesModel = new LoadableDetachableModel<Integer>() {
			@Override
			protected Integer load() {
				return daoService.getHorasSemanales(usuario, fechaActual);
			}

		};
		this.horasMesModel = new LoadableDetachableModel<Integer>() {
			@Override
			protected Integer load() {
				return daoService.getHorasMensuales(usuario, fechaActual);
			}

		};

		this.horasDiaModel = new LoadableDetachableModel<Integer>() {
			@Override
			protected Integer load() {
				return daoService.getHorasDiarias(usuario, fechaActual);

			}

		};

		this.listViewContainer = new WebMarkupContainer("listViewContainer");
		this.listViewContainer.setOutputMarkupId(true);
		labelContainer = new WebMarkupContainer("labelContainer");

		final SlickGrid slickGrid = new SlickGrid("slickGrid", lstDataModel, slickGridJsonCols) {

			@Override
			protected void onInfoSend(AjaxRequestTarget target,
					String realizar, Data data) {
				try {
					if (realizar == "borrar") {

						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss.SSS");
						java.util.Date parsedDate = dateFormat.parse(data
								.getId());
						java.sql.Timestamp timestamp = new java.sql.Timestamp(
								parsedDate.getTime());
						daoService.borrarEntrada(timestamp);
					} else {

						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss.SSS");
						java.util.Date parsedDate = dateFormat.parse(data
								.getId());
						java.sql.Timestamp timestamp = new java.sql.Timestamp(
								parsedDate.getTime());
						System.out.println(data.getFecha());
						dateFormat = new SimpleDateFormat("dd/MM/yyyy");
						Date fecha = dateFormat.parse(data.getFecha());
						Actividad actividad = daoService.getActividad(data
								.getActividad());
						Proyecto proyecto = daoService.getProyecto(data
								.getProyecto());
						Integer ticket;
						if (data.getTicket().isEmpty()) {
							ticket = 0;
						} else {
							ticket = Integer.parseInt(data.getTicket());
						}

						Entrada entrada = new Entrada(timestamp, proyecto,
								actividad, data.getDuration(),
								data.getDescripcion(), ticket,
								data.getTicketExt(), data.getSistExt(),
								usuario, fecha);
						daoService.modificarEntrada(entrada);
					}
//					List<Data> entradas;
					fechaActual = new LocalDate();
					lstDataModel.detach();
//					entradas = daoService.getEntradasDia(EntradasPage.this.usuario, fechaActual);
//					lstDataModel.setObject(entradas);
					target.add(listViewContainer);
					target.add(labelContainer);
					target.add(form);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}

		};

		slickGrid.setOutputMarkupId(true);

		form = new EntradaForm("form") {
			@Override
			protected void onSubmit(AjaxRequestTarget target, EntradaForm form) {
				daoService.agregarEntrada(form.getModelObject(), usuario);
				lstDataModel.detach();
				target.add(listViewContainer);
				target.add(labelContainer);
			}
		};

		horasAcumuladasDia = new Label("horasAcumuladasDia", this.horasDiaModel);
		horasAcumuladasDia.setOutputMarkupId(true);

		horasAcumuladasSemana = new Label("horasAcumuladasSemana",
				this.horasSemanalesModel);
		horasAcumuladasSemana.setOutputMarkupId(true);

		horasAcumuladasMes = new Label("horasAcumuladasMes", this.horasMesModel);
		horasAcumuladasMes.setOutputMarkupId(true);

		final RadioGroup<String> radiog = new RadioGroup<String>(
				"selectorTiempo", Model.of(new String()));

		Radio<String> dia = new Radio<String>("dia", Model.of("Dia"));
		Radio<String> semana = new Radio<String>("semana", Model.of("Semana"));
		Radio<String> mes = new Radio<String>("mes", Model.of("Mes"));

		radioContainer = new WebMarkupContainer("radioContainer");
		radioContainer.setOutputMarkupId(true);
		radioContainer.add(radiog);
		add(radioContainer);

		radiog.add(dia.add(new AjaxEventBehavior("onchange") {
			protected void onEvent(AjaxRequestTarget target) {
//				List<Data> entradas = daoService.getEntradasDia(
//						EntradasPage.this.usuario, fechaActual);
//				lstDataModel.setObject(entradas);
				lstDataModel.detach();
				target.add(listViewContainer);
				target.add(labelContainer);
			}
		}));

		radiog.add(semana.add(new AjaxEventBehavior("onchange") {
			protected void onEvent(AjaxRequestTarget target) {
				List<Data> entradas = daoService.getEntradasSemanales(
					EntradasPage.this.usuario, fechaActual);
					lstDataModel.setObject(daoService.toJson(entradas));
					target.add(listViewContainer);
					target.add(labelContainer);
			}
		}));
		radiog.add(mes.add(new AjaxEventBehavior("onchange") {
			protected void onEvent(AjaxRequestTarget target) {
				List<Data> entradas = daoService.getEntradasMensuales(
					EntradasPage.this.usuario, fechaActual);
					lstDataModel.setObject(daoService.toJson(entradas));
					target.add(listViewContainer);
					target.add(labelContainer);

			}
		}));

		listViewContainer.setOutputMarkupId(true);
		listViewContainer.add(slickGrid);
		add(listViewContainer);

		labelContainer.add(horasAcumuladasDia);
		labelContainer.add(horasAcumuladasSemana);
		labelContainer.add(horasAcumuladasMes);
		labelContainer.setOutputMarkupId(true);

		add(labelContainer);
		add(form);

	}

	public abstract class EntradaForm extends Form<Entrada> {
		public IModel<Entrada> entradaModel = new CompoundPropertyModel<Entrada>(
				new Entrada());
		public DropDownChoice<Actividad> comboActividad;
		public DropDownChoice<Proyecto> comboProyecto;
		public TextField<String> ticketExt;
		public DropDownChoice<String> sistemaExterno;
		public TextField<String> duracion;
		public TextField<String> ticketBZ;
		public YuiDatePicker fecha;
		public TextArea<String> nota;

		public EntradaForm(String id) {
			super(id);
			this.setDefaultModel(this.entradaModel);

			// TODO esto va en la db
			ArrayList<String> sistExt = new ArrayList<String>();
			sistExt.add("Sistema de Incidencias de YPF");
			sistExt.add("Sistema Geminis de YPF");

			this.comboProyecto = new DropDownChoice<Proyecto>("proyecto",
					daoService.getProyectos(), new IChoiceRenderer<Proyecto>() {
						@Override
						public Object getDisplayValue(Proyecto object) {
							return object.getNombre();
						}

						@Override
						public String getIdValue(Proyecto object, int index) {
							return Integer.toString(object.getIdProyecto());
						}

					});
			this.comboProyecto.setOutputMarkupId(true);
			this.comboProyecto.setRequired(true);
			this.comboProyecto.setLabel(Model.of("Proyecto"));

			this.comboProyecto.add(new AjaxFormComponentUpdatingBehavior(
					"onchange") {
				@Override
				protected void onUpdate(AjaxRequestTarget target) {
					EntradaForm.this.comboActividad.setChoices(daoService
							.getActividades(EntradaForm.this.comboProyecto
									.getModelObject()));
					target.add(EntradaForm.this.comboActividad);
				}
			});

			mensajeProyecto = new Label("mensajeProyecto", "Campo necesario");
			mensajeProyecto.add(new AttributeModifier("style", Model
					.of("display:none")));
			mensajeProyecto.setOutputMarkupId(true);

			this.comboActividad = new DropDownChoice<Actividad>("actividad",
					new ArrayList<Actividad>(),
					new IChoiceRenderer<Actividad>() {
						@Override
						public Object getDisplayValue(Actividad object) {
							return object.getNombre();
						}

						@Override
						public String getIdValue(Actividad object, int index) {
							return Integer.toString(object.getIdActividad());
						}

					});
			this.comboActividad.setOutputMarkupId(true);
			this.comboActividad.setRequired(true);
			this.comboActividad.setLabel(Model.of("Actividad"));

			mensajeActividad = new Label("mensajeActividad", "Campo Necesario");
			mensajeActividad.add(new AttributeModifier("style", Model
					.of("display:none")));
			mensajeActividad.setOutputMarkupId(true);

			sistemaExterno = new DropDownChoice<String>("sistemaExterno",
					sistExt);
			sistemaExterno.setLabel(Model.of("Sistema Externo"));
			sistemaExterno.setOutputMarkupId(true);

			nota = new TextArea<String>("nota");

			duracion = new TextField<String>("duracion");
			duracion.setRequired(true);
			duracion.setOutputMarkupId(true);
			duracion.setLabel(Model.of("Duracion"));
			duracion.add(new NumberCommaBehavior(duracion.getMarkupId()));
			duracion.add(new DurationValidator());

			ticketBZ = new TextField<String>("ticketBZ");
			ticketBZ.setRequired(true);
			ticketBZ.setOutputMarkupId(true);
			ticketBZ.setLabel(Model.of("Ticket Bugzilla"));
			ticketBZ.add(new OnlyNumberBehavior(ticketBZ.getMarkupId()));

			ticketExt = new TextField<String>("ticketExterno");
			ticketExt.setLabel(Model.of("ID Ticket Externo"));
			ticketExt.setOutputMarkupId(true);
			ticketExt.add(new PatternValidator("^[a-z0-9_-]{1,15}$"));

			final YuiDatePicker fecha = new YuiDatePicker("fecha") {

				@Override
				protected void onDateSelect(AjaxRequestTarget target,
						String selectedDate) {
					String json = selectedDate;
					List<String> campos = Arrays.asList(json.split("/"));
					int dia = Integer.parseInt(campos.get(0));
					int mes = Integer.parseInt(campos.get(1));
					int anio = Integer.parseInt(campos.get(2));

					fechaActual = new LocalDate(anio, mes, dia);
					List<Data> data = daoService.getEntradasDia(EntradasPage.this.usuario, fechaActual);
					lstDataModel.detach();
					target.add(listViewContainer);
					target.add(labelContainer);
					target.add(radioContainer);
				}

				@Override
				public DatePickerDTO getDatePickerData() {
					DatePickerDTO dto = new DatePickerDTO();
					dto.setDedicacion(8);
					dto.setUsuario(usuario.getNombre());
					Collection<HorasCargadasPorDia> c = daoService
							.getHorasDiaras(usuario);
					dto.setHorasDia(c);
					return dto;
				}
			};

			fecha.setRequired(true);
			fecha.setLabel(Model.of("Fecha"));

			final FeedbackPanel feedBackPanel = new FeedbackPanel(
					"feedBackPanel");
			feedBackPanel.setOutputMarkupId(true);

			AjaxButton submit = new AjaxButton("submit", this) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
					EntradaForm.this.onSubmit(target, (EntradaForm) form);

					target.add(feedBackPanel);
					lstDataModel.detach();
					target.add(listViewContainer);
					target.add(labelContainer);
					target.add(radioContainer);
					EntradaForm.this.setModelObject(new Entrada());

					if (duracion.isValid()) {
						duracion.add(new AttributeModifier("style", Model
								.of("border-color:none")));
					} else {
						duracion.add(new AttributeModifier("style", Model
								.of("border-style:solid; border-color:red;")));
					}

					if (ticketExt.isValid()) {
						ticketExt.add(new AttributeModifier("style", Model
								.of("border-color:none")));
					} else {
						ticketExt.add(new AttributeModifier("style", Model
								.of("border-style:solid; border-color:red;")));
					}

					if (fecha.isValid()) {
						fecha.add(new AttributeModifier("style", Model
								.of("border-color:none")));
					} else {
						fecha.add(new AttributeModifier("style", Model
								.of("border-style:solid; border-color:red;")));
					}

					if (comboProyecto.isValid()) {
						mensajeProyecto.add(new AttributeModifier("style",
								Model.of("display:none")));
					}

					if (comboActividad.isValid()) {
						mensajeActividad.add(new AttributeModifier("style",
								Model.of("display:none")));
					}

					target.add(EntradaForm.this);
					//TODO agregar cada add en cajitas separandoles del datepicker
				}

				@Override
				protected void onError(AjaxRequestTarget target, Form<?> form) {

					if (!duracion.isValid()) {
						duracion.add(new AttributeModifier("style", Model
								.of("border-style:solid; border-color:red;")));
					} else {
						duracion.add(new AttributeModifier("style", Model
								.of("border-color:none")));
					}

					if (!ticketExt.isValid()) {
						ticketExt.add(new AttributeModifier("style", Model
								.of("border-style:solid; border-color:red;")));
					} else {
						ticketExt.add(new AttributeModifier("style", Model
								.of("border-color:none")));
					}

					if (!fecha.isValid()) {
						fecha.add(new AttributeModifier("style", Model
								.of("border-style:solid; border-color:white;")));
					} else {
						fecha.add(new AttributeModifier("style", Model
								.of("border-color:none")));
					}

					if (comboProyecto.isValid()) {
						mensajeProyecto.add(new AttributeModifier("style",
								Model.of("display:none")));
					} else {
						mensajeProyecto.add(new AttributeModifier("style",
								Model.of("font-weight:bold;color:red")));
					}

					if (comboActividad.isValid()) {
						mensajeActividad.add(new AttributeModifier("style",
								Model.of("display:none")));
					} else {
						mensajeActividad.add(new AttributeModifier("style",
								Model.of("font-weight:bold;color:red")));
					}

					target.add(feedBackPanel);
					//target.add(fecha);
					target.add(duracion);
					target.add(ticketExt);
					target.add(mensajeProyecto);
					target.add(mensajeActividad);
//					target.add(radioContainer);
				}

			};

			AjaxButton limpiar = new AjaxButton("limpiar", this) {

				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
					EntradaForm.this.setModelObject(new Entrada());
					target.add(form);
					target.add(feedBackPanel);
				}

			};

			limpiar.setDefaultFormProcessing(false);

			add(mensajeProyecto);
			add(mensajeActividad);
			add(comboProyecto);
			add(comboActividad);
			add(duracion);
			add(fecha);
			add(nota);
			add(ticketBZ);
			add(sistemaExterno);
			add(ticketExt);
			add(feedBackPanel);
			this.add(new OnRelatedFieldsNullValidator(sistemaExterno,
					ticketExt,
					"Debe poner un sistema externo para poder poner un ticket externo"));
			this.add(new OnRelatedFieldsNullValidator(ticketExt,
					sistemaExterno,
					"Debe ingresar un ticket con ese sistema externo elegido"));
			add(submit);
			add(limpiar);

			this.setOutputMarkupId(true);
		}

		protected abstract void onSubmit(AjaxRequestTarget target,
				EntradaForm form);

	}

	private String getColumns() {
		List<Proyecto> list = this.daoService.getProyectos();
		String actividades = "";
		for (Proyecto proyecto : list) {
			actividades += proyecto.toString();
			actividades += this.daoService.getActividades(proyecto).toString();
		}
		;

		String lista = daoService.getProyectos().toString();
		String proyectos = lista.subSequence(1, lista.length() - 1).toString();
		Columna columna = new Columna("delCol", "Delete", 60, 60, 60, null,
				"del", "Slick.Formatters.DeleteButton", null, null, null);
		Columna columna2 = new Columna("duration", "Duracion", 60, 60, 60,
				"cell-title", "duration", null, "Slick.Editors.Text",
				"requiredDurationValidator", null);
		Columna columna3 = new Columna("actividad", "Actividad", 125, 100, 200,
				"cell-title", "actividad", null,
				"Slick.Editors.SelectRelatedEditor", "requiredFieldValidator",
				actividades);
		Columna columna4 = new Columna("proyecto", "Proyecto", 135, 100, 200,
				"cell-title", "proyecto", null, "Slick.Editors.SelectEditor",
				"requiredFieldValidator", proyectos);
		Columna columna5 = new Columna("fecha", "Start", 60, 60, 60, null,
				"fecha", null, "Slick.Editors.Date", "requiredFieldValidator",
				null);
		Columna columna6 = new Columna("ticket", "Ticket", 50, 50, 50,
				"cell-title", "ticket", null, "Slick.Editors.Text", null, null);
		Columna columna7 = new Columna("ticketExt", "TicketExt", 80, 80, 100,
				"cell-title", "ticketExt", null, "Slick.Editors.TextTicketExt",
				null, null);
		Columna columna8 = new Columna("sistExt", "SistExt", 80, 80, 80,
				"cell-title", "sistExt", null, "Slick.Editors.Text", null, null);
		Columna columna9 = new Columna("descripcion", "Desc", 80, 80, 80, null,
				"descripcion", null, "Slick.Editors.LongText", null, null);
		ArrayList<Columna> columnas = new ArrayList<Columna>();
		columnas.add(columna);
		columnas.add(columna5);
		columnas.add(columna4);
		columnas.add(columna3);
		columnas.add(columna2);
		columnas.add(columna9);
		columnas.add(columna6);
		columnas.add(columna8);
		columnas.add(columna7);
		String texto = "[";
		for (Columna col : columnas) {
			texto += "{id:\"" + col.getId() + "\", name: \"" + col.getName()
					+ "\", width: " + col.getWidth() + ", minWidth: "
					+ col.getMinWidth() + ", maxWidth: " + col.getMaxWidth()
					+ ", cssClass: \"" + col.getCssClass() + "\", field: \""
					+ col.getField() + "\",formatter: " + col.getFormatter()
					+ ", editor: " + col.getEditor() + ", validator: "
					+ col.getValidator() + ", options: \"" + col.getOptions()
					+ "\"},";
		}
		texto += "]";
		return texto;
	}

}
