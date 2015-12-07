/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.sirec.ejb.servicios;


import ec.sirec.ejb.entidades.Patente;
import ec.sirec.ejb.entidades.PatenteArchivo;
import ec.sirec.ejb.facade.PatenteArchivoFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author Darwin Aldas
 */
@Stateless
@LocalBean
public class PatenteArchivoServicio {

   
    @EJB
    private PatenteArchivoFacade patenteArchivoDao;
    String ENTIDAD_PAT_ARCHIVO = "PatenteArchivo";
       // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public List<PatenteArchivo> listarArchivoPorPatente(Patente codigo) throws Exception {
        return patenteArchivoDao.listarPorCampoOrdenada(ENTIDAD_PAT_ARCHIVO, "patCodigo", codigo, "patCodigo", "asc");
        //return archivoServicio.listarTodos();
    }

    public String guardarArchivo(PatenteArchivo archivo) throws Exception {
        patenteArchivoDao.crear(archivo);
        return "Se ha creado el archivo" + archivo.getPatarcCodigo();
    }

    public String eliminarArchivo(PatenteArchivo archivo) throws Exception {
        patenteArchivoDao.eliminarGenerico(ENTIDAD_PAT_ARCHIVO, "arcCodigo", archivo.getPatarcCodigo());
        return "se ha eliminado el archivo" + archivo;
    }

    
}
