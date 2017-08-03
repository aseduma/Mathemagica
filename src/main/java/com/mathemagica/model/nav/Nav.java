package com.mathemagica.model.nav;

import com.mathemagica.model.user.Role;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

/**
 * Created by Azael on 2017/08/02.
 */
@Entity
@Table(name = "nav", catalog = "app")
public class Nav {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "href", nullable = false)
    @ColumnDefault("'#'")
    private String href;

    @Column(name = "icon", nullable = false)
    private String icon;

    @Column(name = "css_class", nullable = false)
    @ColumnDefault("'parent'")
    private String cssClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role", nullable = false)
    private Role role;

    @Column(name = "enabled")
    @ColumnDefault("true")
    private Boolean enabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
