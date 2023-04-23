package hkmu.comps380f.model;


import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import org.hibernate.annotations.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Photo {
    @Id
    @GeneratedValue
    @ColumnDefault("random_uuid()")
    private UUID id;

    @Column(name = "username", insertable=false, updatable=false)
    private String name ;
    @ManyToOne
    @JoinColumn(name = "username")
    private Users customer;

    private String content_type;
    private String description ;
    private String filename ;

    @Column(name = "photo")
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private byte[] photo;

    @CreationTimestamp
    private Date create_time ;
    @UpdateTimestamp
    private Date update_time ;

    @OneToMany(mappedBy = "photoId", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<Comment> comments = new ArrayList<>();


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }


    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Users getCustomer() {
        return customer;
    }

    public void setCustomer(Users customer) {
        this.customer = customer;
    }
}
