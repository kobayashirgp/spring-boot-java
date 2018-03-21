package br.com.alura.listavip;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.alura.enviadorEmail.EmailService;
import br.com.alura.listavip.model.Convidado;
import br.com.alura.listavip.service.ConvidadoService;

@Controller
public class ConvidadoController {
	
	@Autowired
	private ConvidadoService service;

	@RequestMapping("/")
	public String ola(){
		return "index";
	}
	@RequestMapping("/lista")
	public ModelAndView listaConvidados(Convidado convidado){
		ModelAndView mv = new ModelAndView("listaconvidados");	
		
		Iterable<Convidado> lista = service.getAll();
		mv.addObject("convidados", lista);
		mv.addObject("convidado",convidado);
		
		return mv;
	}
	@RequestMapping(value="salvar",method = RequestMethod.POST)
	public ModelAndView salvar(Model model, Convidado convidado){
			
		
		System.out.println(convidado.getNome());
		try{
			service.save(convidado);
			new EmailService().enviar(convidado.getNome(), convidado.getEmail());
		}catch(Error e){
			return listaConvidados(convidado);
		}
		
		return new ModelAndView("redirect:/lista");
	}
}
