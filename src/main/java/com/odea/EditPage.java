package com.odea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.odea.domain.Entrada;
import com.odea.domain.Proyecto;
import com.odea.services.DAOService;

/**
 * User: pbergonzi Date: 14/06/12 Time: 10:59
 */
public class EditPage extends BasePage {
	@SpringBean
	private transient DAOService daoService;
	private IModel<Entrada> entradaModel;

	public EditPage(final PageParameters parameters) {
		this.entradaModel = new CompoundPropertyModel<Entrada>(
				new LoadableDetachableModel<Entrada>() {
					@Override
					protected Entrada load() {
						return daoService.buscarEntrada(parameters.get("id")
								.toLong());
					}
				});
		this.preparePage();
	}

	private void preparePage() {
		System.out.println(this.entradaModel.getObject());
		add(new FeedbackPanel("feedback"));
		ArrayList<Proyecto> proyectosList = new ArrayList<Proyecto>(
				daoService.getProyectos());

		Enumeration<Proyecto> q = Collections.enumeration(daoService
				.getProyectos());

		List<Proyecto> lista3 = Collections.list(q);

		Form<Entrada> form = new Form<Entrada>("form", entradaModel) {
			@Override
			protected void onSubmit() {
				Entrada t = getModelObject();
				daoService.modificarEntrada(t);
				setResponsePage(AgregarEntradasPage.class);
			}
		};

		DropDownChoice<Proyecto> proyecto = new DropDownChoice<Proyecto>("proyecto", daoService.getProyectos(),new IChoiceRenderer<Proyecto>() {
			@Override
			public Object getDisplayValue(Proyecto object) {
				return object.getNombre();
			}

			@Override
			public String getIdValue(Proyecto object, int index) {
				return Integer.toString(object.getIdProyecto());
			}
			
		});
		
		TextField usuario = new TextField("usuario.nombre");
		Button submit = new Button("submit");

		form.add(proyecto);
		form.add(submit);
		form.add(usuario);

		add(form);
	}

}
