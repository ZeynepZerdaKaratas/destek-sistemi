import { useState, useEffect } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css';

function Uygulama() {
  const API_URL = "http://192.168.1.100:8080"; 

  // --- VERİLER ---
  const [talepler, setTalepler] = useState([]);
  const [analizEdiliyor, setAnalizEdiliyor] = useState(false);
  const [yeniKayit, setYeniKayit] = useState({ baslik: '', aciklama: '', email: '' });
  const [bildirim, setBildirim] = useState(null);

  // İstatistikler
  const toplamSayi = talepler.length;
  const acilSayisi = talepler.filter(t => t.oncelik === 'ACIL').length;
  const beklemeSayisi = talepler.filter(t => t.durum === 'ACIK').length;

  // Sayfa açılınca verileri getir
  useEffect(() => {
    verileriGetir();
  }, []);

  const verileriGetir = () => {
    axios.get(`${API_URL}/api/talepler`)
      .then(cevap => setTalepler(cevap.data.reverse())) 
      .catch(hata => console.error(hata));
  };

  const formuGonder = (e) => {
    e.preventDefault();
    setAnalizEdiliyor(true); 

    setTimeout(() => {
      axios.post(`${API_URL}/api/talepler`, yeniKayit)
        .then(() => {
          setAnalizEdiliyor(false);
          setYeniKayit({ baslik: '', aciklama: '', email: '' }); // Formu temizle
          setBildirim("✅ Talep Başarıyla İletildi!");
          verileriGetir(); // Listeyi güncelle
          setTimeout(() => setBildirim(null), 3000);
        })
        .catch(() => {
            setAnalizEdiliyor(false);
            alert("Hata oluştu! Java çalışıyor mu?");
        });
    }, 1500);
  };

  return (
    <div className="container-fluid min-vh-100 bg-light py-4" style={{fontFamily: 'Segoe UI, sans-serif'}}>
      
      {/* ÜST BAŞLIK */}
      <div className="d-flex justify-content-between align-items-center mb-4 px-3">
        <div>
          <h2 className="fw-bold text-primary"><i className="bi bi-robot me-2"></i>Akıllı Destek Otomasyonu</h2>
          <p className="text-muted">Yapay Zeka Destekli Talep Yönetim Sistemi</p>
        </div>
        <button className="btn btn-dark" onClick={() => window.location.reload()}>
           <i className="bi bi-arrow-clockwise me-2"></i>Yenile
        </button>
      </div>

      <div className="row g-4">
        
        {/* --- SOL TARAF: FORM (ÇALIŞAN) --- */}
        <div className="col-md-4">
          <div className="card shadow border-0 h-100">
            <div className="card-header bg-primary text-white py-3">
              <h5 className="mb-0"><i className="bi bi-pen-fill me-2"></i>Yeni Talep Oluştur</h5>
            </div>
            <div className="card-body p-4">
              
              {bildirim && <div className="alert alert-success">{bildirim}</div>}

              <form onSubmit={formuGonder}>
                <div className="mb-3">
                  <label className="fw-bold small text-muted">E-POSTA</label>
                  <input type="email" className="form-control bg-light" required placeholder="ornek@sirket.com"
                    value={yeniKayit.email} onChange={e => setYeniKayit({...yeniKayit, email: e.target.value})} />
                </div>
                <div className="mb-3">
                  <label className="fw-bold small text-muted">KONU</label>
                  <input type="text" className="form-control bg-light" required placeholder="Örn: Yazıcı bozuldu"
                    value={yeniKayit.baslik} onChange={e => setYeniKayit({...yeniKayit, baslik: e.target.value})} />
                </div>
                <div className="mb-4">
                  <label className="fw-bold small text-muted">AÇIKLAMA</label>
                  <textarea className="form-control bg-light" rows="5" required placeholder="Sorunu detaylı anlatın..."
                    value={yeniKayit.aciklama} onChange={e => setYeniKayit({...yeniKayit, aciklama: e.target.value})}></textarea>
                </div>
                <button type="submit" className="btn btn-primary w-100 py-2 fw-bold" disabled={analizEdiliyor}>
                   {analizEdiliyor ? 'AI Analiz Ediyor...' : 'GÖNDER'}
                </button>
              </form>
            </div>
          </div>
        </div>

        {/* --- SAĞ TARAF: DASHBOARD & LİSTE (YÖNETİCİ) --- */}
        <div className="col-md-8">
          
          {/* İstatistik Kutuları */}
          <div className="row g-3 mb-4">
            <div className="col-md-4"><div className="card bg-primary text-white p-3 shadow-sm text-center"><h3>{toplamSayi}</h3><small>TOPLAM</small></div></div>
            <div className="col-md-4"><div className="card bg-danger text-white p-3 shadow-sm text-center"><h3>{acilSayisi}</h3><small>ACİL DURUM</small></div></div>
            <div className="col-md-4"><div className="card bg-success text-white p-3 shadow-sm text-center"><h3>{beklemeSayisi}</h3><small>AKTİF</small></div></div>
          </div>

          {/* Liste */}
          <div className="card shadow border-0">
            <div className="card-header bg-white py-3">
              <h5 className="mb-0 fw-bold text-secondary">Talep Havuzu (Yönetici Görünümü)</h5>
            </div>
            <div className="table-responsive">
              <table className="table table-hover align-middle mb-0">
                <thead className="bg-light text-secondary small">
                  <tr><th>ID</th><th>GÖNDEREN</th><th>KONU / AI ANALİZİ</th><th>ÖNCELİK</th><th>DURUM</th></tr>
                </thead>
                <tbody>
                  {talepler.map(t => (
                    <tr key={t.id} className={t.oncelik === 'ACIL' ? 'table-danger' : ''}>
                      <td className="fw-bold">#{t.id}</td>
                      <td>{t.email}</td>
                      <td><span className="fw-bold">{t.baslik}</span><br/><small className="text-muted">{t.aciklama}</small></td>
                      <td><span className={`badge ${t.oncelik==='ACIL'?'bg-danger':'bg-secondary'}`}>{t.oncelik}</span></td>
                      <td>{t.durum}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>

        </div>
      </div>
    </div>
  );
}

export default Uygulama;