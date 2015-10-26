/*
 * Licensed to the University of California, Berkeley under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package tachyon.client;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import tachyon.TachyonURI;
import tachyon.thrift.FileInfo;

/**
 * Interface for Tachyon client APIs
 *
 * As of 0.8, replaced by {@link tachyon.client.file.TachyonFileSystemCore}
 */
@Deprecated
interface TachyonFSCore extends Closeable {
  /**
   * Creates a new file in the file system.
   *
   * @param path The path of the file
   * @param ufsPath The path of the file in the under file system. If this is empty, the file does
   *        not exist in the under file system yet.
   * @param blockSizeByte The size of the block in bytes. It is -1 iff ufsPath is non-empty
   * @param recursive Creates necessary parent folders if true, not otherwise
   * @return The file id, which is globally unique
   * @throws IOException if the operation fails
   */
  long createFile(TachyonURI path, TachyonURI ufsPath, long blockSizeByte, boolean recursive)
      throws IOException;

  /**
   * Deletes a file or folder.
   *
   * @param fileId The id of the file / folder. If it is not -1, path parameter is ignored
   *        Otherwise, the method uses the path parameter.
   * @param path The path of the file / folder. It could be empty iff id is not -1
   * @param recursive If fileId or path represents a non-empty folder, delete the folder recursively
   *        or not
   * @return true if deletes successfully, false otherwise
   * @throws IOException if the operation fails

   */
  boolean delete(long fileId, TachyonURI path, boolean recursive) throws IOException;

  /**
   * Gets the FileInfo object that represents the fileId, or the path if fileId is -1.
   *
   * @param fileId the file id of the file or folder
   * @param path the path of the file or folder. valid iff fileId is -1
   * @return the FileInfo of the file or folder, null if the file or folder does not exist
   * @throws IOException when the operation fails
   */
  FileInfo getFileStatus(long fileId, TachyonURI path) throws IOException;

  /** Returns a URI whose scheme and authority identify this FileSystem. */
  TachyonURI getUri();

  /**
   * If the <code>path</code> is a directory, return all the direct entries in it. If the
   * <code>path</code> is a file, return its FileInfo.
   *
   * @param path the target directory/file path
   * @return A list of FileInfo, null if the file or folder does not exist
   * @throws IOException when the operation fails
   */
  List<FileInfo> listStatus(TachyonURI path) throws IOException;

  /**
   * Creates a folder.
   *
   * @param path the path of the folder to be created
   * @param recursive creates necessary parent folders if true, not otherwise
   * @return true if the folder is created successfully or already existing. false otherwise
   * @throws IOException if the operation fails
   */
  boolean mkdirs(TachyonURI path, boolean recursive) throws IOException;

  /**
   * Renames a file or folder to another path.
   *
   * @param fileId The id of the source file / folder. If it is not -1, path parameter is ignored
   *        Otherwise, the method uses the srcPath parameter.
   * @param srcPath the path of the source file / folder. It could be empty iff id is not -1
   * @param dstPath the path of the destination file / folder. It could be empty iff id is not -1
   * @return true if renames successfully, false otherwise
   * @throws IOException if the operation fails
   */
  boolean rename(long fileId, TachyonURI srcPath, TachyonURI dstPath) throws IOException;

 /**
  * Frees memory of a file or folder.
  *
  * @param fileId the id of the file / folder. If it is not -1, path parameter is ignored
  *        Otherwise, the method uses the path parameter.
  * @param path the path of the file / folder. It could be empty iff id is not -1
  * @param recursive if fileId or path represents a non-empty folder, free the folder recursively
  *        or not
  * @return true if in-memory free successfully, false otherwise
  * @throws IOException if the operation fails
  */
  boolean freepath(long fileId, TachyonURI path, boolean recursive) throws IOException;
}
