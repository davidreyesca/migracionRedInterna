package Controlador;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class OtrosActualizacion {

    public OtrosActualizacion() 
    {
    }
    String tipoPersonaParticipante , nombreFisicaParticipante , apPaFisicaParticipante , apMaFisicaParticipante , nombreMoralParticipante ;
    String observaciones, tipoOtros;
    String sSQL="";
    int noExpediente= AbrirExpediente.getNoExpedinte();
    int IDCliente=1;
    int numeroparticipantes;
    String[][] participantesdatos;
    boolean validacionfinal=true;
    

    public void getParticipantes(String[][] participantes)
    {
        this.participantesdatos = participantes;
    } 

    public void gettipoOtros(String tipoOtros)
    {
        this.tipoOtros = tipoOtros;
    }
    public void getObservaciones(String observaciones)
    {
        this.observaciones = observaciones;
    }
    public void getNumeroParticipantes(int numeroparticipantes)
    {
        this.numeroparticipantes = numeroparticipantes;
    }
    public void guardarTablaOtros()
    {
        String mensaje ="Los datos de la tabla Otros se han actualizado correctamente";
        ConexionMySql mysql = new ConexionMySql();
        Connection cn = mysql.getConection();
        sSQL= "UPDATE otros SET TipoOtro=?, Observaciones=? WHERE IDNoExpediente = '" + AbrirExpediente.getNoExpedinte() + "'";        
        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setString(1, tipoOtros);
            pst.setString(2, observaciones);
            int validacion = pst.executeUpdate();
            if (validacion>0)
            {
                System.out.println(mensaje);
                validacionfinal = true;
                mysql.desconectar();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Hubo un error al insertar los datos en la tabla otros.");
                validacionfinal = false;
            }
        } catch (HeadlessException | SQLException e) 
        {
            JOptionPane.showMessageDialog(null, "ERROR! " + e);
            validacionfinal = false;
        }
    }
    public void guardarTablaParticipantes()
    {
        if(Vista.OtrosVisualizacion.jRBFisicaParticipante.isSelected()==true)
        {
            tipoPersonaParticipante=Vista.OtrosVisualizacion.jRBFisicaParticipante.getText();
            nombreFisicaParticipante= Vista.OtrosVisualizacion.jTNombreParticipanteFisica.getText();
            apPaFisicaParticipante=Vista.OtrosVisualizacion.jTApPaParticipanteFisica.getText();
            apMaFisicaParticipante=Vista.OtrosVisualizacion.jTApMaParticipanteFisica.getText();
        }
        else if(Vista.OtrosVisualizacion.jRBMoralParticipante.isSelected())
        {
            tipoPersonaParticipante = Vista.OtrosVisualizacion.jRBMoralParticipante.getText();
            nombreMoralParticipante = Vista.OtrosVisualizacion.jTNombreParticipanteMoral.getText();
        }  
        String mensaje ="Los datos de la tabla OTROS se han actualizado correctamente";
        //------------------------------------------------------------------------------------        
        ConexionMySql mysql = new ConexionMySql();
        Connection cn = mysql.getConection();
        sSQL= "INSERT INTO otrosparticipante(IDNoExpediente , TipoCliente, NombreParticipante, ApPaternoParticipante, ApMaternoParticipante) VALUES (?, ?, ?, ?, ?)";        
        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, noExpediente);
            pst.setString(2, tipoPersonaParticipante);
            if("Persona Física".equals(tipoPersonaParticipante))
            {
                pst.setString(3, nombreFisicaParticipante);
                pst.setString(4, apPaFisicaParticipante);
                pst.setString(5, apMaFisicaParticipante);
            }
            if("Persona Moral".equals(tipoPersonaParticipante))
            {
                pst.setString(3, nombreMoralParticipante);
                pst.setString(4, "");
                pst.setString(5, "");
            }
            int validacion = pst.executeUpdate();
            if(numeroparticipantes>0)
            {
                for(int i=0; i<numeroparticipantes; i++)
                {
                        
                                    pst.setInt(1, noExpediente);
                                    pst.setString(2, participantesdatos[i][0]);
                                    System.out.println("Soy " + participantesdatos[i][0]);
                                    String uno=participantesdatos[i][1];
                                    String dos=participantesdatos[i][2];
                                    String tres=participantesdatos[i][3];
                                    if("Persona Física".equals(participantesdatos[i][0]))
                                    {
                                        pst.setString(3, uno);
                                        pst.setString(4, dos);
                                        pst.setString(5, tres);
                                    }
                                    if("Persona Moral".equals(participantesdatos[i][0]))
                                    {
                                        pst.setString(3, uno);
                                        pst.setString(4, "");
                                        pst.setString(5, "");
                                    }
                                    int validacion2 = pst.executeUpdate();
                                    
                                    if (validacion2>0) {
                                        System.out.println("validacion: " + validacion2);
                                        validacionfinal = true;
                                    }
                                    else
                                    {
                                        JOptionPane.showMessageDialog(null, "Hubo un error al insertar los datos en la Tabla OTORGANTES Extras.");
                                        validacionfinal = false;
                                    }                                   
                }
            }
            
            
            if (validacion>0) {
                System.out.println(mensaje);
                validacionfinal = true;
                mysql.desconectar();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Hubo un error al insertar los datos en la Tabla OTROS.");
                validacionfinal = false;
            }
        } catch (HeadlessException | SQLException e) 
        {
            JOptionPane.showMessageDialog(null, "ERROR! " + e);
            validacionfinal = false;
        }     
    }
    public void EliminarTablaParticipantes()
    {
        ConexionMySql mysql = new ConexionMySql();
        Connection cn = mysql.getConection();
        sSQL= "DELETE FROM otrosparticipante WHERE IDNoExpediente = '" + AbrirExpediente.getNoExpedinte() + "'";
        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            int validacion = pst.executeUpdate();
            if (validacion>0) {
                System.out.println("¡Eliminado con exito!");  
                validacionfinal = true;
                mysql.desconectar();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Hubo un error al ACTUALIZAR los datos.");
                validacionfinal = false;
            }
        } catch (HeadlessException | SQLException e) 
        {
            JOptionPane.showMessageDialog(null, "ERROR! " + e);
            validacionfinal = false;
        }
    }
    public void capturaFinal()
    {
        guardarTablaOtros();
        EliminarTablaParticipantes();
        guardarTablaParticipantes();
        if(validacionfinal == true)
        {
            JOptionPane.showMessageDialog(null, "¡Se han actualizado todos los datos correctamente!");
        }else
        {
            JOptionPane.showMessageDialog(null, "NO se han podido completa TODAS las OPERACIONES");
        }
    }
}
