package com.diphot.siu.services.restlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.restlet.engine.io.BioUtils;
import org.restlet.representation.OutputRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;



public class ClientResource extends org.restlet.resource.ClientResource {

	public ClientResource(String uri){
		super(uri);
	}
	
	@Override
	protected Representation toRepresentation(Object source, Variant target) {
		Representation result = null;
		if (source != null) {

			org.restlet.service.ConverterService cs = getConverterService();
			result = cs.toRepresentation(source, target, this);
			if (result != null) {
				try {
					final ByteArrayOutputStream os = new ByteArrayOutputStream();

					BioUtils.copy(result.getStream(), os);
					OutputRepresentation rep = new OutputRepresentation(
							result.getMediaType()) {
						@Override
						public void write(OutputStream outputStream) throws IOException {

							outputStream.write(os.toByteArray());
						}
					};
					rep.setSize(os.size());
					result = rep;
				} catch (IOException e) {
					e.printStackTrace();

				}
			}
		}
		return result;
	}
	
}
