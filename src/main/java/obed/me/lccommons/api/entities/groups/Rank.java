package obed.me.lccommons.api.entities.groups;

import lombok.Data;

import java.util.Set;

@Data
public class Rank {
    private String name;
    private String prefix;
    private String color;
    private Integer priority;
    private Boolean defaultRank;
    private Set<String> permissions;

}
