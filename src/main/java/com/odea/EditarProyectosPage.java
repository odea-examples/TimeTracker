package com.odea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.odea.behavior.focusOnLoad.FocusOnLoadBehavior;
import com.odea.components.dualMultipleChoice.DualMultipleChoice;
import com.odea.domain.Actividad;
import com.odea.domain.Proyecto;
import com.odea.services.DAOService;

public class EditarProyectosPage extends BasePage {

	@SpringBean
	public transient DAOService daoService;
	private IModel<Proyecto> proyectoModel;
	public ListMultipleChoice<Actividad> originals;
	public ListMultipleChoice<Actividad> destinations;

	public EditarProyectosPage() {

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

	public EditarProyectosPage(final PageParameters parameters) {

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
		public List<Actividad> listDestination;
		public List<Actividad> listOriginals;
		public DualMultipleChoice<Actividad> dualMultiple;

		public EditForm(String id, IModel<Proyecto> proyecto) {

			super(id, proyecto);


			RequiredTextField<String> nombre = new RequiredTextField<String>("nombre");
			nombre.add(new FocusOnLoadBehavior());
			
			
			LoadableDetachableModel originalsModel = new LoadableDetachableModel() {
				@Override
				protected Object load() {
					return EditForm.this.obtenerListaOrigen();
				}							
	        };
	        
	        LoadableDetachableModel destinationsModel = new LoadableDetachableModel() {
				@Override
				protected Object load() {
					return EditForm.this.obtenerListaDestino();
				}							
	        };
	        
	        
			dualMultiple = new DualMultipleChoice<Actividad>("dual", originalsModel, destinationsModel);
						
			
			AjaxButton submit = new AjaxButton("submit") {
				@Override
				protected void onSubmit(AjaxRequestTarget target, Form form) {
					EditForm.this.onSubmit(target, (EditForm) form);
					daoService.agregarProyecto(EditForm.this.getModelObject(),
							(Collection<Actividad>) dualMultiple.getDestinations().getChoices());
				}
			};

			
			add(nombre);
			add(dualMultiple);
			add(submit);
		}


		private List<Actividad> obtenerListaDestino() {
			if (proyectoModel.getObject().getIdProyecto() > 0) {
				return daoService.getActividades(proyectoModel.getObject());
			}
			else{
				return new ArrayList<Actividad>();
			}
		}


		private List<Actividad> obtenerListaOrigen() {
			if (proyectoModel.getObject().getIdProyecto() > 0) {
				return daoService.actividadesOrigen(proyectoModel.getObject());
			} else {
				return daoService.getActividades();
			}
		}		
		
		
		protected abstract void onSubmit(AjaxRequestTarget target, EditForm form);

	}
}