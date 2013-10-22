package com.ext.test.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.util.PortalUtil;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.mycompany.cu.dto.PersonaDTO;

@Controller
@RequestMapping("VIEW")
public class DemoController {  

	public DemoController() {
	}  

	@RequestMapping  
	protected String defaultView(PortletRequest request,Model model) {
//		193-27782488-0-17
		System.out.println("dentro del default");
		HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(request);
		HttpSession session=httpServletRequest.getSession();
//		PortletSession session=request.getPortletSession();
		session.setAttribute("listaPersona",listPersona());

		return "view";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(params="action=consultarPersona")
	public String tratar(PortletRequest request,Model model,@RequestParam(value="nombre",required=false)String nombre ){
		System.out.println("Entre al consultarPersona con id: "+nombre);

		HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(request);
		HttpSession session=httpServletRequest.getSession();
//		PortletSession session=request.getPortletSession();
		
		List<PersonaDTO> personas=new ArrayList<PersonaDTO>();
		personas=(List<PersonaDTO>)session.getAttribute("listaPersona");
		//personas=listPersona();
		PersonaDTO pers=new PersonaDTO();
		System.out.println(pers);
		for(PersonaDTO p : personas){
			if(p.getNombre().equals(nombre)){
				pers=p;
			}
		}

		model.addAttribute("persona",pers);
		return "result";
	}

	@RequestMapping(params="action=cud")
	protected String operacionPersona(@ModelAttribute("persona") PersonaDTO persona,
			@RequestParam("operacion")String operacion, Model model){

		System.out.println("Entre al operacionPersona");
		System.out.println("Persona param: "+persona);

		if(operacion.equals("_update")){
			System.out.println("updated");
		}else if(operacion.equals("_create")){
			System.out.println("created");
		}else if(operacion.equals("_delete")){
			System.out.println("deleted");
		}

		model.addAttribute("listPersona",listPersona());

		return "view";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(params="reportType=pdf")
	public @ResponseBody void unknow(PortletRequest request,PortletResponse portletResponse){
		System.out.println("Dentro del metodo para PDF");

		String titlePage = ParamUtil.getString(request, "reportType");
		HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(request);
		HttpServletResponse httpServletResponse = (HttpServletResponse) PortalUtil.getHttpServletResponse(portletResponse);

		HttpSession session=httpServletRequest.getSession();
//		List<PersonaDTO> personas=listPersona();
		List<PersonaDTO> personas=(List<PersonaDTO>)session.getAttribute("listaPersona");

		Document document = new Document();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try{
			PdfWriter.getInstance(document, baos);
			document.open();
			if (titlePage != null && !"".equals(titlePage)) {
				System.out.println("dentro del if");

				//Titulo
				document.add(new Paragraph("Listado de personas"));
				document.add(Chunk.NEWLINE);

				//Recorrido de personas; y pintado
				for(PersonaDTO persona : personas){
					//Persona:
					document.add(new Chunk("Persona: ") );
					document.add(new Chunk(persona.getNombre() +", "+persona.getApellido()));
					//NewLine
					document.add(Chunk.NEWLINE);

					//Telf
					document.add(new Chunk("Telefono: "));
					document.add(new Chunk(Integer.toString(persona.getTelf())));
					document.add(Chunk.NEWLINE);

					document.add(new Chunk("Sexo: "));
					document.add(new Chunk(persona.getSexo()));

					document.add(Chunk.NEWLINE);
					document.add(Chunk.NEWLINE);
				}

				document.add(Chunk.NEWLINE);
			}else{
				System.out.println("tit-arg vacio");
			}

			document.close();
		}catch(DocumentException e){
			e.printStackTrace();
			System.out.println("error en documentException");
		}
		// setting the content type
		//		httpServletResponse.setContentType("application/pdf");
		httpServletResponse.setContentType(HttpHeaders.CONTENT_DISPOSITION);
		httpServletResponse.setContentLength(baos.size());
		try{
			System.out.println("intentando en sendFile");
			String userAgent = httpServletRequest.getHeader("user-agent");
			System.out.println(userAgent);
			int aux=userAgent.indexOf("Opera")+userAgent.indexOf("MSIE");
			System.out.println(aux);
			//Si es Opera se devuelve el doc con .pdf
			if(aux==-1){
				ServletResponseUtil.sendFile(httpServletRequest, httpServletResponse, "GenerateReport.pdf", baos.toByteArray());
			}else{
				if(aux>-1 || aux<=24){
					httpServletResponse.setContentType("application/pdf");
				}
			//Si es un navegador diferente a opera se devuelve sin la extencion .pdf
				ServletResponseUtil.sendFile(httpServletRequest, httpServletResponse, "GenerateReport", baos.toByteArray());
			}

		}catch(IOException e){
			e.printStackTrace();
			System.out.println("error en e");
		}

	}

	private List<PersonaDTO> listPersona(){

		PersonaDTO persona1=new PersonaDTO();
		persona1.setIdPersona(1);
		persona1.setNombre("Bill");
		persona1.setApellido("Gates");
		persona1.setTelf(987654321);
		persona1.setSexo("Masculino");

		PersonaDTO persona2=new PersonaDTO();
		persona2.setIdPersona(2);
		persona2.setNombre("Gregorio");
		persona2.setApellido("waylas");
		persona2.setTelf(887654321);
		persona2.setSexo("Femenino");

		PersonaDTO persona3=new PersonaDTO();
		persona3.setIdPersona(3);
		persona3.setNombre("Clink");
		persona3.setApellido("toPdf");
		persona3.setTelf(687654321);
		persona3.setSexo("Masculino");

		List<PersonaDTO> listPersona = new ArrayList<PersonaDTO>();
		listPersona.add(persona1);
		listPersona.add(persona2);
		listPersona.add(persona3);

		return listPersona;
	}

}
