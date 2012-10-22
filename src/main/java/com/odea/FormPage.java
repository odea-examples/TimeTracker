import java.util.Arrays;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
    

// TODO esto es cuando recien entra(pagina default)           
        
        public class FormPage extends BasePage {
            private transient TicketDAO ticketDAO = TicketDAO.getInstance();
            private IModel<Ticket> ticketModel;         
            public FormPage(){
                this.ticketModel = new CompoundPropertyModel<Ticket>(new LoadableDetachableModel<Ticket>() {
                    @Override
                    protected Ticket load() {
                        return new Ticket(null,null,null,TicketType.BUG,TicketStatus.OPEN);
                    }
                });
                this.preparePage();    
            }
// TODO esto es cuando carga la pagina desde otra pagina, asi que le envia algo ya dado...            
            public FormPage(final PageParameters parameters) {
                this.ticketModel = new CompoundPropertyModel<Ticket>(new LoadableDetachableModel<Ticket>() {
                    @Override
                    protected Ticket load() {
                        return ticketDAO.getTicket(parameters.get("idTicket").toLong());
                    }
                });
                this.preparePage();
            }
;}
// 		TODO lo comente porque no entiendo nada de para que sirve esto, si entendes
//			 modificalo.
            
            
            
            
//            private void preparePage(){
//                add(new BookmarkablePageLink<ListPage>("link",ListPage.class));
//                add(new FeedbackPanel("feedback"));
//                
//                Form<Ticket> form = new Form<Ticket>("form",ticketModel){
//                    @Override
//                    protected void onSubmit() {
//                        Ticket t = getModelObject();
//                        ticketDAO.insertOrUpdate(t);
//                        setResponsePage(ListPage.class);
//                    }
//                };

        
//      TODO aca tenes que poner nuestros propios campos que usamos
                RequiredTextField<String> title = new RequiredTextField<String>("title");
                TextArea<String> description = new TextArea<String>("description");
                DropDownChoice<TicketType> ticketType = new DropDownChoice<TicketType>("type", Arrays.asList(TicketType.values()));
                RadioChoice<TicketStatus> ticketStatus = new RadioChoice<TicketStatus>("status", Arrays.asList(TicketStatus.values()));
                Button submit = new Button("submit");

                form.add(title);
                form.add(description);
                form.add(ticketType);
                form.add(ticketStatus);
                form.add(submit);

                add(form);
            }

        }


        add(form);
    }

}
