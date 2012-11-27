package com.odea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.odea.domain.Actividad;
import com.odea.domain.Proyecto;
import com.odea.services.DAOService;

public class EditProyectosPage extends BasePage {

	@SpringBean
	public transient DAOService daoService;
	private IModel<Proyecto> proyectoModel;
	public ListMultipleChoice<Actividad> originals;
	public ListMultipleChoice<Actividad> destinations;

	public EditProyectosPage() {

		Subject subject = SecurityUtils.getSubject();
		if (!subject.isAuthenticated()) {
			this.redirectToInterceptPage(new LoginPage());
		}

		this.proyectoModel = new CompoundPropertyModel<Proyecto>(
				new LoadableDetachableModel<Proyecto>() {
					@Override
					protected Proyecto load() {
						return new Proyecto(0, "");
					}
				});

		this.preparePage();

	}

	public EditProyectosPage(final PageParameters parameters) {

		Subject subject = SecurityUtils.getSubject();
		if (!subject.isAuthenticated()) {
			this.redirectToInterceptPage(new LoginPage());
		}

		this.proyectoModel = new CompoundPropertyModel<Proyecto>(
				new LoadableDetachableModel<Proyecto>() {
					@Override
					protected Proyecto load() {
						return new Proyecto(parameters.get("proyectoId")
								.toInt(), parameters.get("proyectoNombre")
								.toString());
					}
				});

		this.preparePage();

	}

	private void preparePage() {
		add(new BookmarkablePageLink<ProyectosPage>("link", ProyectosPage.class));
		add(new FeedbackPanel("feedback"));

		EditForm form = new EditForm("form", proyectoModel) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, EditForm form) {
				setResponsePage(ProyectosPage.class);
			}

		};

		form.setOutputMarkupId(true);

		add(form);
	}

	public abstract class EditForm extends Form<Proyecto> {

		public List<Actividad> selectedOriginals;
		public List<Actividad> selectedDestinations;

		public EditForm(String id, IModel<Proyecto> proyecto) {

			super(id, proyecto);

			TextField<String> nombre = new TextField<String>("nombre");
			nombre.add(new FocusOnLoadBehavior());

			originals = new ListMultipleChoice<Actividad>("originals",
					new PropertyModel(this, "selectedOriginals"),
					new LoadableDetachableModel() {

						@Override
						protected Object load() {
							if (proyectoModel.getObject().getIdProyecto() > 0) {
								return daoService.actividadesOrigen(proyectoModel.getObject());
							} else {
								return daoService.getActividades();
							}
						}
					});

			originals.setOutputMarkupId(true);

			destinations = new ListMultipleChoice<Actividad>("destinations",
					new PropertyModel(this, "selectedDestinations"),
					new LoadableDetachableModel() {
						@Override
						protected Object load() {
							if (proyectoModel.getObject().getIdProyecto() > 0) {
								return daoService.getActividades(proyectoModel.getObject());
							}
							return new ArrayList<Actividad>();
						}
					});

			destinations.setOutputMarkupId(true);

			AjaxButton addButton = new AjaxButton("addButton") {
				@Override
				protected void onSubmit(AjaxRequestTarget target, Form form) {
					update(target, selectedOriginals, originals, destinations);
				}
			};

			AjaxButton removeButton = new AjaxButton("removeButton") {
				@Override
				protected void onSubmit(AjaxRequestTarget target, Form form) {
					update(target, selectedDestinations, destinations,
							originals);
				}
			};

			AjaxButton submit = new AjaxButton("submit") {
				@Override
				protected void onSubmit(AjaxRequestTarget target, Form form) {
					EditForm.this.onSubmit(target, (EditForm) form);
					daoService.agregarProyecto(EditForm.this.getModelObject(),
							(Collection<Actividad>) destinations.getChoices());
				}
			};

			add(nombre);
			add(originals);
			add(destinations);
			add(addButton);
			add(removeButton);
			add(submit);

		}

		private void update(AjaxRequestTarget target, List<Actividad> selections, ListMultipleChoice<Actividad> from, ListMultipleChoice<Actividad> to) {
			List<Actividad> choicesTo;
			List<Actividad> choicesFrom;

			for (Actividad destination : selections) {
				choicesTo = (List<Actividad>) to.getChoices();

				if (!choicesTo.contains(destination)) {
					choicesTo.add(destination);

					choicesFrom = (List<Actividad>) from.getChoices();
					choicesFrom.remove(destination);

					Collections.sort(choicesTo);
					Collections.sort(choicesFrom);

					from.setChoices(choicesFrom);
					to.setChoices(choicesTo);

				}
			}

			target.add(to);
			target.add(from);
		}

		protected abstract void onSubmit(AjaxRequestTarget target, EditForm form);

	}
}