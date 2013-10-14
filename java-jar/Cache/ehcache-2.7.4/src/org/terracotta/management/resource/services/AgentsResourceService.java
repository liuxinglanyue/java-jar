/* All content copyright (c) 2003-2012 Terracotta, Inc., except as may otherwise be noted in a separate copyright notice.  All rights reserved.*/

package org.terracotta.management.resource.services;

import org.terracotta.management.resource.AgentEntity;
import org.terracotta.management.resource.AgentMetadataEntity;
import org.terracotta.management.resource.Representable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;

/**
 * <p>
 * A common resource service interface for agents representing Terracotta monitorable entities. This resource service
 * provides discovery of the agents and a top level summary of the {@link Representable} objects they publish.
 * </p>
 * 
 * @author brandony
 * 
 */
public interface AgentsResourceService {
  /**
   * <p>
   * A top level resource that provides each agents root {@link Representable} objects.
   * </p>
   * 
   * @param info - {@link UriInfo} for this resource request
   * @return a collection of {@link AgentEntity} objects when successful.
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  Collection<AgentEntity> getAgents(@Context UriInfo info);

  /**
   * <p>
   * A resource that provides discovery of all agents and reveals metadata about each agents API and state.
   * <p>
   * 
   * @param info - {@link UriInfo} for this resource request
   * @return a collection of {@link AgentMetadataEntity} objects when successful.
   */
  @GET
  @Path("/info")
  @Produces(MediaType.APPLICATION_JSON)
  Collection<AgentMetadataEntity> getAgentsMetadata(@Context UriInfo info);
}
