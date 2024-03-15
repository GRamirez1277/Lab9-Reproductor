package Prueba2Lab;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Port;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.images.Artwork;

public class Reproductor extends javax.swing.JFrame {
    ArrayList<String>listaCanciones;
    private int currentIndex;
    private BasicPlayer played;
    private boolean detenida=false;
    private AudioFile AF;
    private File mp3File;
    private Playlist list=new Playlist();
    private Canciones cancionActual=null;
    private final Archivo player;
    private Short x = 0;
    private String artista;
    private String album;
    private long duracion;
    private final DefaultListModel listado=new DefaultListModel();
    /**
     * Creates new form Reprodcutor
     */
    public Reproductor() {
        initComponents();
        tituloCancion.setEditable(false);
        artistaCancion.setEditable(false);
        albumCancion.setEditable(false);
        tituloCancion.setEditable(false);
        player=new Archivo(this);
        cancionesARepdroducir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                JList lista=(JList)evt.getSource();
                if (evt.getClickCount()==1) {
                    int index = lista.locationToIndex(evt.getPoint());
                    if (index!=-1) {
                        cancionActual=list.get_cancion(index);
                        x = 0;
                        playActionPerformed(null);
                    }
                }
            }
        });
    }
    
    

    private void VolumeSliderChanged(javax.swing.event.ChangeEvent evt) {
        float newVolume=volumen.getValue()/100.0f;
        adjustSystemVolume(newVolume);
    }
    
    private void datosCancion() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException{
        AF=AudioFileIO.read(mp3File);

            Tag tag=AF.getTag();

           artista=tag.getFirst(FieldKey.ARTIST);
           album=tag.getFirst(FieldKey.ALBUM);
           System.out.println(album+artista);
           if(!"".equals(artista)){
           artistaCancion.setText(artista);
           }else{
           artistaCancion.setText("Artista desconocido");       
           }
           if(!"".equals(album)){
           albumCancion.setText(album);
           }else{
           albumCancion.setText("Album desconocido");   
           }
           duracion=AF.getAudioHeader().getTrackLength();
           long minutos;
           long segundos;
           if(duracion>60){
               minutos=duracion/60;
               segundos=duracion%60;
               jTextField3.setText(minutos+":"+segundos);
           }else{
               jTextField3.setText("0:"+duracion+" segundos");
           }
           Artwork fotoAlbum = tag.getFirstArtwork();
            if (fotoAlbum!=null) {
                byte[] binaryData = fotoAlbum.getBinaryData();
                ByteArrayInputStream stream = new ByteArrayInputStream(binaryData);
                BufferedImage image = ImageIO.read(stream);
                ImageIcon icon = new ImageIcon(image);
                Image images = icon.getImage();
                ImageIcon img2=new ImageIcon(images.getScaledInstance(221, 176, Image.SCALE_SMOOTH));
                fotoCancion.setIcon(img2);
            } else {
                fotoCancion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/notiene.png")));
            }
    }

    private void adjustSystemVolume(float newVolume) {
        try {
            Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
            for (Mixer.Info info : mixerInfo) {
                Mixer mixer = AudioSystem.getMixer(info);
                if (mixer.isLineSupported(Port.Info.SPEAKER)) {
                    Port port=(Port) mixer.getLine(Port.Info.SPEAKER);
                    port.open();

                    if(port.isControlSupported(FloatControl.Type.VOLUME)) {
                        FloatControl volumeControl = (FloatControl) port.getControl(FloatControl.Type.VOLUME);
                        volumeControl.setValue(newVolume);
                    }

                    port.close();
                }
            }
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    private String datosCancion(File file) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException{
        AF = AudioFileIO.read(file);

            Tag tag = AF.getTag();

           artista = tag.getFirst(FieldKey.ARTIST);
           album = tag.getFirst(FieldKey.ALBUM);

           if("".equals(artista)){
           artista="Artista desconocido";
           }
           if("".equals(album)){
           album="Album desconocido";
           }
           duracion=AF.getAudioHeader().getTrackLength();
           String tiempo;
           long minutos;
           long segundos;
           if(duracion>60){
               minutos=duracion/60;
               segundos=duracion%60;
               tiempo=minutos+":"+segundos;
           }else{
               tiempo=duracion+"";
           }

            return artista+" - "+album+" - "+tiempo;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        stop = new javax.swing.JButton();
        add = new javax.swing.JButton();
        play = new javax.swing.JButton();
        next = new javax.swing.JButton();
        volumen = new javax.swing.JSlider();
        tituloCancion = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        artistaCancion = new javax.swing.JTextField();
        albumCancion = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        cancionesARepdroducir = new javax.swing.JList<>();
        fotoCancion = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\USER\\Downloads\\Imágenes descargadas\\Mini Windows\\ReproductorMusica\\Volume.png")); // NOI18N

        stop.setBackground(new java.awt.Color(255, 255, 255));
        stop.setIcon(new javax.swing.ImageIcon("C:\\Users\\USER\\Downloads\\Imágenes descargadas\\Mini Windows\\ReproductorMusica\\Stop.png")); // NOI18N
        stop.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        stop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stopMouseClicked(evt);
            }
        });

        add.setBackground(new java.awt.Color(255, 255, 255));
        add.setIcon(new javax.swing.ImageIcon("C:\\Users\\USER\\Downloads\\Imágenes descargadas\\Mini Windows\\ReproductorMusica\\Add.png")); // NOI18N
        add.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addMouseClicked(evt);
            }
        });
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        play.setBackground(new java.awt.Color(255, 255, 255));
        play.setIcon(new javax.swing.ImageIcon("C:\\Users\\USER\\Downloads\\Imágenes descargadas\\Mini Windows\\ReproductorMusica\\Play.png")); // NOI18N
        play.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        play.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                playMouseClicked(evt);
            }
        });
        play.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playActionPerformed(evt);
            }
        });

        next.setBackground(new java.awt.Color(255, 255, 255));
        next.setIcon(new javax.swing.ImageIcon("C:\\Users\\USER\\Downloads\\Imágenes descargadas\\Mini Windows\\ReproductorMusica\\Next.png")); // NOI18N
        next.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        next.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nextMouseClicked(evt);
            }
        });
        next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextActionPerformed(evt);
            }
        });

        volumen.setValue(100);

        tituloCancion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tituloCancionActionPerformed(evt);
            }
        });

        jLabel1.setText("Nombre de la canción:");

        jLabel3.setText("Artista:");

        jLabel4.setText("Albúm:");

        jTextField3.setText("00:00");

        cancionesARepdroducir.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        jScrollPane2.setViewportView(cancionesARepdroducir);

        fotoCancion.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel5.setText("Duración:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(volumen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(add)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fotoCancion, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(tituloCancion, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(81, 81, 81)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jLabel4)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(albumCancion, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jLabel3)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(artistaCancion, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(54, 54, 54)
                                    .addComponent(jTextField3)
                                    .addGap(55, 55, 55))))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(play)
                                .addGap(150, 150, 150))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(stop)
                                .addGap(160, 160, 160)
                                .addComponent(next)
                                .addGap(31, 31, 31))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(fotoCancion, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tituloCancion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(artistaCancion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(albumCancion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(play)
                            .addComponent(next)
                            .addComponent(stop))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(add)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(volumen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9))))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void stopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stopMouseClicked
        Stop();
        detenida=false;
    }//GEN-LAST:event_stopMouseClicked

    private void Stop() {
    try {
        player.control.stop();
        x = 0;
    } catch (BasicPlayerException e) {
        e.printStackTrace();
    }
}

    
    private void addMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseClicked
        Add();
    }//GEN-LAST:event_addMouseClicked

    private void Add(){
        JFileChooser fileChooser = new JFileChooser("/Música");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivo MP3", "mp3","mp3"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(true);
        int seleccion = fileChooser.showOpenDialog(this);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File files[] = fileChooser.getSelectedFiles();
            boolean noMp3 = false, repetidos = false;

            for (File file : files) {
                try {
                    String name = file.getName();
                    if (name.length() < 4 || !name.substring(name.length() - 4, name.length()).equalsIgnoreCase(".mp3")) {
                        noMp3 = true;
                        continue;
                    }
                    if (list.buscar(file.getName(), file.getPath())) {
                        repetidos = true;
                        continue;
                    }
                    list.insertar(file.getName(), file.getPath());
                    System.out.println(file.getPath());
                    listado.addElement(file.getName()+" - "+datosCancion(file));
                    cancionesARepdroducir.setModel(listado);
                    
                } catch (CannotReadException ex) {
                    Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (TagException ex) {
                    Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ReadOnlyFileException ex) {
                    Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidAudioFrameException ex) {
                    Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (noMp3) {
                JOptionPane.showMessageDialog(null, "Archivo(s) no mp3", "Atención", 0);
            }
            if (repetidos) {
                JOptionPane.showMessageDialog(null, "Archivos repetidos", "Atención", 0);
            }
        }
    }
    
    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addActionPerformed

    private void playMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playMouseClicked
        Play();
    }//GEN-LAST:event_playMouseClicked

    private void Play(){
        Logger.getLogger("javazoom.jlgui.basicplayer").setLevel(Level.OFF);
    detenida = true;
    if (list.IsEmpty()) {
        JOptionPane.showMessageDialog(null, "No hay canciones en cola", "Atención", 1);
    } else {
        try {
            if (cancionActual == null) {
                cancionActual = list.first;
            }
            mp3File = new File(cancionActual.direccion);
            datosCancion();
            try {
                if (x == 0) {
                    player.control.open(new URL("file:///" + cancionActual.direccion));
                    player.control.play();
                    tituloCancion.setText(cancionActual.nombre);
                    x = 1;
                } else {
                    if (x == 1) {
                        player.control.pause();
                        x = 2;
                    } else {
                        player.control.resume();
                        x = 1;
                    }
                }
            } catch (BasicPlayerException ex) {
                JOptionPane.showMessageDialog(null, "Error al abrir\nla canción", "Atención", 1);
                x = 0;
            } catch (MalformedURLException ex) {
                Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Error al abrir la dirección de la cancion", "Atención", 1);
                x = 0;
            }
        } catch (CannotReadException ex) {
            Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TagException ex) {
            Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ReadOnlyFileException ex) {
            Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAudioFrameException ex) {
            Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }
    private void playActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playActionPerformed
        
    }//GEN-LAST:event_playActionPerformed

    private void nextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nextMouseClicked
        playNextSong();
    }//GEN-LAST:event_nextMouseClicked

    private void playNextSong() {
    if (!list.IsEmpty()) {
        currentIndex++;
        cancionActual = list.get_cancion(currentIndex);
        playActionPerformed(null);
    }
}
    private void nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nextActionPerformed

    private void tituloCancionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tituloCancionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tituloCancionActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Reproductor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reproductor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reproductor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reproductor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reproductor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JTextField albumCancion;
    private javax.swing.JTextField artistaCancion;
    private javax.swing.JList<String> cancionesARepdroducir;
    private javax.swing.JLabel fotoCancion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel jTextField3;
    private javax.swing.JButton next;
    private javax.swing.JButton play;
    private javax.swing.JButton stop;
    private javax.swing.JTextField tituloCancion;
    private javax.swing.JSlider volumen;
    // End of variables declaration//GEN-END:variables
}
