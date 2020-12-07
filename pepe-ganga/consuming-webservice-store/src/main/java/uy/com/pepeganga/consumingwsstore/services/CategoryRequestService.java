package uy.com.pepeganga.consumingwsstore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import uy.com.pepeganga.business.common.entities.UpdatesOfSystem;
import uy.com.pepeganga.business.common.utils.date.DateTimeUtilsBss;
import uy.com.pepeganga.consumingwsstore.entities.TempCategory;
import uy.com.pepeganga.consumingwsstore.conversions.ConvertModels;
import uy.com.pepeganga.consumingwsstore.repositories.ITempCategoryRepository;
import uy.com.pepeganga.consumingwsstore.repositories.IUpdatesSystemRepository;
import uy.com.pepeganga.consumingwsstore.wsdl.categories.CargaZafrasExecute;
import uy.com.pepeganga.consumingwsstore.wsdl.categories.CargaZafrasExecuteResponse;


public class CategoryRequestService extends WebServiceGatewaySupport {	
	
	@Autowired
	ITempCategoryRepository categoryClient;

	@Autowired
	IUpdatesSystemRepository updateSysRepo;
	
	 public List<TempCategory> getCategories() {
		
		 CargaZafrasExecute request = new CargaZafrasExecute();		 
		 CargaZafrasExecuteResponse response = (CargaZafrasExecuteResponse) getWebServiceTemplate()
		        .marshalSendAndReceive("http://201.217.140.35/agile/acargazafras.aspx", request);
		 
		 List<TempCategory> responseList = ConvertModels.convetToCategoryEntityList(response.getSdtcategorias().getSdtCategoriasSdtCategoria());
		 return responseList;
		   
	 }
	 
	 /*Implementar aca evento para que esto se ejecute solo cada cierto tiempo*/
		public boolean storeCategories(UpdatesOfSystem data) {
			try {
				boolean perfect = true;
				List<TempCategory> categoryList = getCategories();

				if (categoryList == null || categoryList.isEmpty()) {
					logger.warn("Lista de Categorias del servicio del almacén vacios o nulos ");
					String data1 = data.getMessage();
					data.setMessage(data1 + " Lista de Categorias del servicio del almacén vacios o nulos;");
					data.setEndDate(DateTimeUtilsBss.getDateTimeAtCurrentTime().toDate());
					data.setFinishedSync(false);
					updateSysRepo.save(data);
					return false;
				}

				for (TempCategory category : categoryList) {
					if (categoryClient.save(category) == null)
						perfect = false;
				}
				// Logear si todo fue almacenado correctamente
				if (!perfect) {
					logger.error("Error almacenando categorias del servicio del almacén");
					String data1 = data.getMessage();
					data.setMessage(data1 + " Error almacenando categorias del servicio del almacén;");
					data.setEndDate(DateTimeUtilsBss.getDateTimeAtCurrentTime().toDate());
					data.setFinishedSync(false);
					updateSysRepo.save(data);
					return false;
				}
				return true;
			}catch (Exception e) {
				logger.error("Error almacenando categorias del servicio del almacén");
				String data1 = data.getMessage();
				data.setMessage(data1 + " Error almacenando categorias del servicio del almacén;");
				data.setEndDate(DateTimeUtilsBss.getDateTimeAtCurrentTime().toDate());
				data.setFinishedSync(false);
				updateSysRepo.save(data);
				return false;
			}
		}
}
