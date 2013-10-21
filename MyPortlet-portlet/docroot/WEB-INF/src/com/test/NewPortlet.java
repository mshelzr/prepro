package com.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.util.PortalUtil;

@Controller
@RequestMapping("VIEW")
public class NewPortlet {

	public NewPortlet() {
		System.out.println("Init NewPortlet");
	}

	@RequestMapping
	protected String defaultView(Model model) {
		String toModel="Hola, Elvis";
		model.addAttribute("toModel",toModel);
		return "view";
	}

	@RequestMapping(params="action=topdf")
	public void toPdf(ResourceRequest requ, ResourceResponse resp){
		
		PortletRequest req = requ;
		HttpServletRequest httpServletRequest = (HttpServletRequest) PortalUtil.getHttpServletRequest(req);
		PortletResponse response = resp;
		HttpServletResponse httpServletResponse = (HttpServletResponse) PortalUtil.getHttpServletResponse(response);
		Document document = new Document();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		try {
			PdfWriter.getInstance(document, baos);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		document.open();

		String titlePage = ParamUtil.getString(req, "reportType");

		if (titlePage != null && !"".equals(titlePage)) {
			Chunk chunk = new Chunk(titlePage);
			Paragraph para = new Paragraph(32);
			para.setSpacingBefore(50);
			para.setSpacingAfter(50);
			para.add(chunk);
			try {
				document.add(para);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		PdfPTable table = new PdfPTable(2);

		table.addCell(new PdfPCell(new Paragraph("name")));
		table.addCell(new PdfPCell(new Paragraph("age")));

		try {
			document.add(table);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		document.close();
		httpServletResponse.setHeader("Expires", "0");
		httpServletResponse.setHeader("Cache-Control",
				"must-revalidate, post-check=0, pre-check=0");
		httpServletResponse.setHeader("Pragma", "public");
		// setting the content type
		httpServletResponse.setContentType("application/pdf");
		httpServletResponse.setContentLength(baos.size());
		try {
			ServletResponseUtil.sendFile(httpServletRequest, httpServletResponse, "client.pdf", baos.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
