/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.erc.pftps.commands;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.ftpserver.command.AbstractCommand;
import org.apache.ftpserver.ftplet.DefaultFtpReply;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpFile;
import org.apache.ftpserver.ftplet.FtpReply;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.impl.FtpIoSession;
import org.apache.ftpserver.impl.FtpServerContext;
import org.apache.ftpserver.impl.LocalizedDataTransferFtpReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * <code>UNZP &lt;SP&gt; &lt;pathname&gt; &lt;CRLF&gt;</code><br>
 * 
 * This command causes the server-DTP to transfer a copy of the file, specified
 * in the pathname, to the server- or user-DTP at the other end of the data
 * connection. The status and contents of the file at the server site shall be
 * unaffected.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class DZPC extends AbstractCommand {

	/**
	 * Execute command.
	 */
	public void execute(final FtpIoSession session, final FtpServerContext context, final FtpRequest request)
			throws IOException, FtpException {

		session.resetState();

		// argument check
		String fileName = request.getArgument();
		if (fileName == null) {
			session.write(LocalizedDataTransferFtpReply.translate(session, request, context,
					FtpReply.REPLY_501_SYNTAX_ERROR_IN_PARAMETERS_OR_ARGUMENTS, "UNZP", null, null));
			return;
		}

		FtpFile homeDir = session.getFileSystemView().getHomeDirectory();
		String dstDir = homeDir.getPhysicalFile() + File.separator + ".cache" + File.separator + fileName;
		FileUtils.deleteDirectory(new File(dstDir));
		session.write(new DefaultFtpReply(FtpReply.REPLY_200_COMMAND_OKAY, "Zip cache for " + fileName + " deleted"));

	}

}
