package com.hg.pj.gallery;

import java.util.List;

public interface GalleryMapper {
	public List<GalleryFile> get();

	public int upload(GalleryFile gf);

	public abstract int delete(GalleryFile gf);

}
