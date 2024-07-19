package dev.httpmarco.polocloud.base.node;

import dev.httpmarco.polocloud.base.Node;
import dev.httpmarco.polocloud.base.node.endpoints.ExternalNodeEndpoint;
import dev.httpmarco.polocloud.base.node.endpoints.LocalNodeEndpoint;
import dev.httpmarco.polocloud.base.node.endpoints.NodeEndpoint;
import dev.httpmarco.polocloud.base.node.packets.NodeSituationCallbackPacket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public final class NodeProvider {

    @Setter
    private @Nullable NodeEndpoint headNodeEndpoint;
    // self node endpoints
    private final LocalNodeEndpoint localEndpoint;
    // all other connected nodes with her data and connection (if present)
    private final Set<ExternalNodeEndpoint> externalNodeEndpoints;

    public NodeProvider() {
        var nodeModel = Node.instance().nodeModel();

        this.localEndpoint = new LocalNodeEndpoint(nodeModel.localNode());
        this.localEndpoint.server().responder("cluster-node-situation", property -> new NodeSituationCallbackPacket(localEndpoint.situation()));

        this.externalNodeEndpoints = nodeModel.cluster().endpoints().stream().map(ExternalNodeEndpoint::new).collect(Collectors.toSet());
    }

    public void initialize() {
        NodeConnectionFactory.bindCluster(this);
    }

    public boolean isHead() {
        return this.localEndpoint.equals(headNodeEndpoint);
    }
}